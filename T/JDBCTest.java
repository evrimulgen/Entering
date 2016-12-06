import org.junit.Before;
import org.junit.Test;

import com.mics.utils.JDBCUtil;


public class JDBCTest {
	private JDBCUtil jdbcUtil = null;
	String filename;
	String filepath;
	
	@Before
	public void init(){
		filename = "abcde";
		filepath = "D:/abcde";
		jdbcUtil = new JDBCUtil();
	}
	
	@Test
	public void test(){
		jdbcUtil.executeUpdate(filename,filepath);
	}

}
