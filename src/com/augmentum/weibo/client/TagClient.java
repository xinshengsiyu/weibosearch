package com.augmentum.weibo.client;

import java.util.List;

import com.augmentum.weibo.dao.MySQLCommonDao;
import com.augmentum.weibo.util.Constant;
import com.augmentum.weibo.util.FileUtil;

public class TagClient {
	public List<String> keyWords() {
		System.out.println("==========读取关键字================");
		List<String> keyWords = null;
		String tableName = FileUtil.getProperty(Constant.TAG_TABLE_NAME);
		String sql = "select distinct name from `" + tableName + "`";
		keyWords = MySQLCommonDao.getInstance().doQuery(sql, "name");
		System.out.println("==========读取关键字完毕================");
		return keyWords;
	}
}
