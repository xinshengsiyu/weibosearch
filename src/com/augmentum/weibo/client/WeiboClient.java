package com.augmentum.weibo.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.augmentum.weibo.dao.MongoCommonDao;
import com.augmentum.weibo.dao.exception.MongoDBException;
import com.augmentum.weibo.exception.WeiboException;
import com.augmentum.weibo.util.Constant;
import com.augmentum.weibo.util.FileUtil;
import com.augmentum.weibo.util.RequestUtil;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;

public class WeiboClient {
	private static Logger logger = Logger.getLogger(WeiboClient.class);

	public List<DBObject> getWeibo(String keyWords) {
		System.out.println("==============爬取新浪页面，当前关键字:" + keyWords
				+ "===================");
		String weiboUrl = FileUtil.getProperty(Constant.SEARCH_URL);
		String keyword;
		try {
			keyword = URLEncoder.encode(keyWords, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(keyWords);
			throw new WeiboException("keyword encode to utf8 error");
		}
		String searchUrl = weiboUrl + keyword;
		Document doc = RequestUtil.getSearchResultPage(searchUrl, null);
		Elements elements = doc.getElementsByTag("script");
		List<DBObject> list = new ArrayList<DBObject>();

		for (Element element : elements) {
			String html = element.html();
			try {
				html = html.replace(
						"STK && STK.pageletM && STK.pageletM.view(", "");
				html = html.substring(0, html.length() - 1);
				DBObject weibo = (DBObject) JSON.parse(html);
				if ("pl_wb_feedlist".equals(weibo.get("pid").toString())) {
					list.add(weibo);
				}
			} catch (JSONParseException e) {

			} catch (StringIndexOutOfBoundsException e) {

			} catch (Exception e) {
				logger.error(e);
				throw new WeiboException(e);
			}
		}

		return list;
	}

	public List<String> pareHtml2Ids(DBObject weibo) {
		System.out.println("====解析页面,获取消息Id=====");
		String pageHtml = weibo.get("html").toString();
		Document doc = Jsoup.parse(pageHtml);
		Elements elements = doc.getElementsByAttributeValue("class",
				FileUtil.getProperty(Constant.WEIBO_CLASS_NAME));
		List<String> ids = new ArrayList<String>();
		for (Element element : elements) {
			String id = element.attr("mid");
			if (!"".equals(id) && null != id) {
				ids.add(id);
			}
		}
		System.out.println("====解析页面完毕,共:" + ids.size() + "=====");
		return ids;
	}

	public boolean createWeibo(String weiboId) {
		System.out.println("===========调用新浪API获取消息详情================");
		try {
			DBObject obj = MongoCommonDao.getInstance().findByAttribute(
					"SocialMessage", "idstr", weiboId);
			if (null != obj) {
				System.out.println("=========已经存在的消息，不再存储=======");
				return true;
			}
			String json = RequestUtil.getWeiboFromAPi(weiboId);
			System.out.println("===========调用新浪API获取消息详情完毕================");
			return MongoCommonDao.getInstance().save("SocialMessage", json);
		} catch (MongoDBException e) {
			logger.error(e.getMessage());
			throw new WeiboException(e);
		}
	}

	public void searchByKeyWord(String keyWord) {
		List<DBObject> list = this.getWeibo(keyWord);
		List<String> ids = new ArrayList<String>();

		for (DBObject dbObject : list) {
			ids.addAll(this.pareHtml2Ids(dbObject));
		}

		for (String id : ids) {
			this.createWeibo(id);
		}
	}

	public static void main(String[] args) {
		System.out.println("==========start========");
		TagClient client = new TagClient();
		WeiboClient weibo = new WeiboClient();
		List<String> keyWords = client.keyWords();
		for (String keyWord : keyWords) {
			weibo.searchByKeyWord(keyWord);
		}
		System.out.println("=========end==========");
	}

}
