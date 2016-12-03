import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.mics.http.HttpRequest;


public class HttpTest {
	
	private HttpRequest httpRequest;
	
	@Before
	public void init(){
		httpRequest = new HttpRequest();
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			httpRequest.DoctorLogin();
			httpRequest.getPatientList();
//			Thread.sleep(1000);
			httpRequest.getPatientList();
//			Thread.sleep(1000);
			httpRequest.getPatientList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
