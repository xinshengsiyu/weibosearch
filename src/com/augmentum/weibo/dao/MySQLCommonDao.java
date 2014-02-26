package com.augmentum.weibo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.augmentum.weibo.client.TagClient;
import com.augmentum.weibo.exception.WeiboException;

public class MySQLCommonDao {
	private static Logger logger = Logger.getLogger(TagClient.class);
	private static MySQLCommonDao dao = null;

	private MySQLCommonDao() {
	}

	public static MySQLCommonDao getInstance() {
		if (dao == null) {
			dao = new MySQLCommonDao();
		}
		return dao;
	}

	public List<String> doQuery(String sql, String cloumName) {
		MySQLDBUtil util = new MySQLDBUtil();
		ResultSet rs = util.doQuery(sql);
		List<String> keyWords = new ArrayList<String>();

		try {
			while (rs.next()) {
				keyWords.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new WeiboException(e);
		} finally {
			util.close();
		}

		return keyWords;
	}

}
