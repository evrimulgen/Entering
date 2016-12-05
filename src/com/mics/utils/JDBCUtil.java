package com.mics.utils;

import java.sql.Connection;
import java.sql.Statement;

public class JDBCUtil {
	
	 public int executeUpdate(String filename,String filepath)  
	    {  
		    StringBuilder sqlBuilder = new StringBuilder();
		 	sqlBuilder.append("INSERT INTO name_path VALUES ('");
		 	sqlBuilder.append(filename);
		 	sqlBuilder.append("','");
		 	sqlBuilder.append(filepath);
		 	sqlBuilder.append("')");
		 	
	        int result = 0;  
	        Connection conn = null;  
	        Statement st = null;  
	        try  
	        {  
	            conn = ConnectionBuilder.getConnection();  
	            st = conn.createStatement();  
	            result = st.executeUpdate(sqlBuilder.toString());  
	            st.close();  
	            conn.close();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }  
}
