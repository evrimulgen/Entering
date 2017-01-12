package com.mics.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mics.conf.BaseConf;

public class GetStatistics {

	private String mysqlUrl;
	private String mysqlUserName;
	private String mysqlPass;
	private String oracleUrl;
	private String oracleUserName;
	private String oraclePass;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private ResultSet rs = null;
	private Connection conn = null;
	private Statement stmt = null;
	private BaseConf baseConf = null;

	public GetStatistics() {
		this.baseConf = BaseConf.getInstance();
		this.init();
	}

	private void init() {
		this.mysqlUrl = baseConf.getMysqlUrl();
		this.mysqlUserName = baseConf.getMysqlUserName();
		this.mysqlPass = baseConf.getMysqlPass();
		this.oracleUrl = baseConf.getOracleUrl();
		this.oracleUserName = baseConf.getOracleUserName();
		this.oraclePass = baseConf.getOraclePass();
	}

//	public int getIUploadCount() {
//		int count = 0;
//		try {
//			count = httpRequest.getCountUserOfUploadByDate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return count;
//	}

	public int getRegisterCount() {
		int count = 0;
		ResultSet rs = this.getOracleResulrSet();
		try {
			if (rs != null && rs.next()) {
				count = rs.getInt("VID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeSQL();
		}
		
		return count;
	}

	public int getCollection() {
		int count = 0;
		ResultSet rs = this.getMysqlResultSet();
		try {
			if (rs != null && rs.next()) {
				count = rs.getInt(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeSQL();
		}

		return count;
	}

	private ResultSet getMysqlResultSet() {
		List<String> dateRange = this.getDateRange();

		try {

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(this.mysqlUrl, this.mysqlUserName, this.mysqlPass);
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT(p.pk) FROM `patient` p where p.updated_time BETWEEN '" + dateRange.get(0)
					+ "' AND '" + dateRange.get(1) + "'";
			rs = stmt.executeQuery(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	private ResultSet getOracleResulrSet(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(this.oracleUrl, this.oracleUserName, this.oraclePass);
			stmt = conn.createStatement();
			String sql = "SELECT COUNT(VID) VID FROM (SELECT DISTINCT(VID) FROM VIEW_DX)";
			rs = stmt.executeQuery(sql);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	private List<String> getDateRange() {
		List<String> list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		String startDate = this.df.format(calendar.getTime());
		list.add(startDate);

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		String endDate = df.format(calendar.getTime());
		list.add(endDate);
		return list;
	}

	private void closeSQL() {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
