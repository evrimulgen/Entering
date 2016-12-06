package com.mics.scp;

import java.io.File;
import java.io.IOException;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.data.VR;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomOutputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;
import org.dcm4che3.net.Association;
import org.dcm4che3.net.PDVInputStream;
import org.dcm4che3.net.Status;
import org.dcm4che3.net.pdu.PresentationContext;
import org.dcm4che3.net.service.BasicCStoreSCP;
import org.dcm4che3.net.service.DicomServiceException;
import org.dcm4che3.util.AttributesFormat;
import org.dcm4che3.util.SafeClose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CStoreSCPService {
	private static final Logger LOG = LoggerFactory.getLogger(com.mics.scp.StoreSCP.class);
	
	private static final String PART_EXT = ".part";
	private File storageDir;
	private int status = 0;
	private AttributesFormat filePathFormat = null;
	
	public CStoreSCPService(File storageDir, int status) {
		this.storageDir = storageDir;
		this.status = status;
	}
	
	
	public final BasicCStoreSCP cstoreSCP = new BasicCStoreSCP("*") {

		@Override
		protected void store(Association as, PresentationContext pc, Attributes rq, PDVInputStream data, Attributes rsp)
				throws IOException {
			rsp.setInt(Tag.Status, VR.US, status);
			if (storageDir == null)
				return;

			String cuid = rq.getString(Tag.AffectedSOPClassUID);
			String iuid = rq.getString(Tag.AffectedSOPInstanceUID);
			String tsuid = pc.getTransferSyntax();
			File file = new File(storageDir, iuid + PART_EXT);
			try {
				storeTo(as, as.createFileMetaInformation(iuid, cuid, tsuid), data, file);
				File newFile = new File(storageDir, filePathFormat == null ? iuid : filePathFormat.format(parse(file)));
				renameTo(as, file, newFile);
				System.out.println("=============" + newFile.getName());
//				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//				DicomInputStream dicomInputStream = new DicomInputStream(newFile);
//				Attributes attributes = dicomInputStream.readDataset(-1, Tag.PixelData);
//				System.out.println(attributes.getString(Tag.PatientName));
//				System.out.println(attributes.getString(Tag.StudyInstanceUID));
//				System.out.println(attributes.getString(Tag.SeriesInstanceUID));
//				System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			} catch (Exception e) {
				deleteFile(as, file);
				throw new DicomServiceException(Status.ProcessingFailure, e);
			}
		}

	};

	
	
	private void storeTo(Association as, Attributes fmi, PDVInputStream data, File file) throws IOException {
//		LOG.info("{}: M-WRITE {}", as, file);
		file.getParentFile().mkdirs();
		DicomOutputStream out = new DicomOutputStream(file);
		try {
			out.writeFileMetaInformation(fmi);
			data.copyTo(out);
		} finally {
			SafeClose.close(out);
		}
	}
	
	private static void renameTo(Association as, File from, File dest) throws IOException {
//		LOG.info("{}: M-RENAME {} to {}", as, from, dest);
		if (!dest.getParentFile().mkdirs())
			dest.delete();
		if (!from.renameTo(dest))
			throw new IOException("Failed to rename " + from + " to " + dest);
	}
	
	private static Attributes parse(File file) throws IOException {
		DicomInputStream in = new DicomInputStream(file);
		try {
			in.setIncludeBulkData(IncludeBulkData.NO);
			return in.readDataset(-1, Tag.PixelData);
		} finally {
			SafeClose.close(in);
		}
	}

	private static void deleteFile(Association as, File file) {
		if (file.delete())
			LOG.info("{}: M-DELETE {}", as, file);
		else
			LOG.warn("{}: M-DELETE {} failed!", as, file);
	}

	public void setFilePathFormat(AttributesFormat filePathFormat) {
		this.filePathFormat = filePathFormat;
	}
}
