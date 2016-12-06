package com.mics.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class JDBCUtil {
	
	 public int executeUpdate(String filename,String filepath)  
	    {  
		 	String sql = "INSERT INTO name_path(filename,filepath) VALUES(?,?)";
	        int result = 0;  
	        Connection conn = null;  
	        PreparedStatement pst = null;
	        try  
	        {  
	            conn = ConnectionBuilder.getConnection();  
	            pst = conn.prepareStatement(sql);
	            pst.setString(1,filename);
	            pst.setString(2,filepath);
	            result = pst.executeUpdate();
	            conn.close();  
	        }  
	        catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        }  
	        return result;  
	    }  
}
