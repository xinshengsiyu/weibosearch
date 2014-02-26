package com.augmentum.weibo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.augmentum.weibo.exception.WeiboException;
import com.augmentum.weibo.util.Constant;
import com.augmentum.weibo.util.FileUtil;

public class MySQLDBUtil {

	private static Logger logger = Logger.getLogger(MySQLDBUtil.class);

	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://"
			+ FileUtil.getProperty(Constant.MYSQL_HOST) + ":"
			+ FileUtil.getProperty(Constant.MYSQL_PORT) + "/"
			+ FileUtil.getProperty(Constant.MYSQL_NAME);
	private static final String username = FileUtil
			.getProperty(Constant.MYSQL_USERNAME);
	private static final String password = FileUtil
			.getProperty(Constant.MYSQL_PASSWORD);

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Connection getDBConnection() {
		try {
			Class.forName(driver);
			if (conn == null) {
				conn = DriverManager.getConnection(url, username, password);
			}
		} catch (Exception e) {
			logger.error(e);
			throw new WeiboException(e);
		}
		return conn;
	}

	public ResultSet doQuery(String sql) {
		Connection conn = getDBConnection();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			logger.error(e);
			throw new WeiboException(e);
		}
		return rs;
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			logger.error(e);
			throw new WeiboException(e);
		}
	}

}
