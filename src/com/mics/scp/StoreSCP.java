package com.mics.scp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.dcm4che3.net.ApplicationEntity;
import org.dcm4che3.net.Connection;
import org.dcm4che3.net.Device;
import org.dcm4che3.net.TransferCapability;
import org.dcm4che3.net.service.BasicCEchoSCP;
import org.dcm4che3.net.service.DicomServiceRegistry;

import com.mics.conf.BaseConf;

public class StoreSCP {

	private final Device device = new Device("storescp");
	private final ApplicationEntity ae = new ApplicationEntity("*");
	private final Connection conn = new Connection();
	
	private CStoreSCPService cStoreSCPService;
	
	public StoreSCP() throws IOException {
		cStoreSCPService = new CStoreSCPService(new File(BaseConf.Scp_StorageDirectory), BaseConf.Scp_CStore_Status);
		device.setDimseRQHandler(createServiceRegistry());
		device.addConnection(conn);
		device.addApplicationEntity(ae);
		ae.setAssociationAcceptor(true);
		ae.addConnection(conn);
	}

	private DicomServiceRegistry createServiceRegistry() {
		DicomServiceRegistry serviceRegistry = new DicomServiceRegistry();
		serviceRegistry.addDicomService(new BasicCEchoSCP());
		serviceRegistry.addDicomService(cStoreSCPService.cstoreSCP);
		return serviceRegistry;
	}

	public void startSCPServer(String[] args) {
		try {
			StoreSCP main = new StoreSCP();
			main.ae.setAETitle(BaseConf.Scp_AETitle);
	        main.conn.setPort(BaseConf.Scp_port);
			main.conn.setMaxOpsInvoked(0);
	        main.conn.setMaxOpsPerformed(0);
			main.ae.addTransferCapability(new TransferCapability(null, "*", TransferCapability.Role.SCP, "*"));
			ExecutorService executorService = Executors.newCachedThreadPool();
			ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
			main.device.setScheduledExecutor(scheduledExecutorService);
			main.device.setExecutor(executorService);
			main.device.bindConnections();
		} catch (Exception e) {
			System.err.println("storescp: " + e.getMessage());
			e.printStackTrace();
			System.exit(2);
		}
	}
}
