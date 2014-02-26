package com.augmentum.weibo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.augmentum.weibo.exception.WeiboException;

public class RequestUtil {

	//private static Logger logger = Logger.getLogger(RequestUtil.class);
	
    protected static CookieStore generateCookieStore() {
        CookieStore cookieStore = new BasicCookieStore();
//        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "33DCE0F9D43FD40425EEB8B263940CDA");
//        cookie.setDomain("api.weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("SINAGLOBAL", "1429884738754.481.1392891754579");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("SSOLoginState", "1393309890");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("SUE", "es%3D6328918f3a9570b825b695ee911fadd6%26ev%3Dv1%26es2%3Df1c052d8233b14d5871dc364ff5cd72d%26rs0%3DCm38EIU4J8EqdQlWc8TswI3ejIMFiwNk0hQzCH%252FbkyL5Fn1UsE0IScrwF7EYA1pT8rGmb3%252FH%252FjsAEsC6y5Z4NuevwuSlbuvyZviu38m92BbUjheZ9pk46imimKSr96b0wiolIj%252FZW1PB3%252FIW%252F1a%252FXrKhgNEjGL0PmaChw7E6RTs%253D%26rv%3D0");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("SUP", "cv%3D1%26bt%3D1393313688%26et%3D1393400088%26d%3Dc909%26i%3D3df6%26us%3D1%26vf%3D0%26vt%3D0%26ac%3D0%26st%3D0%26uid%3D1616571592%26name%3Dxinsheng_yu%2540163.com%26nick%3D%25E5%25B0%258F%25E8%2580%2581%25E9%25BC%25A0%26fmp%3D%26lcp%3D2011-12-02%252015%253A44%253A35");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("login_sid_t", "a2f8100f325d1ea641b271f7b588ec6c");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("myuid", "1616571592");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
//        
//        cookie = new BasicClientCookie("un", "xinsheng_yu@163.com");
//        cookie.setDomain(".weibo.com");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);

        return cookieStore;
    }

	public static Document getSearchResultPage(String uri,
			Map<String, String> params) {
		String url = generateUri(uri, params);
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new WeiboException(e);
		}
		return doc;
	}

	public static String getWeiboFromAPi(String weiboId) {
		String baseUri = FileUtil.getProperty(Constant.WEIBO_API);
		String appKey = FileUtil.getProperty(Constant.SINA_APP_KEY);
		Map<String, String> params = new HashMap<String, String>();
		params.put("source", appKey);
		params.put("id", weiboId);

		return sendGetRequest(baseUri, params);
	}

	public static String sendGetRequest(String baseUri,
			Map<String, String> params) {
		String url = generateUri(baseUri, params);
//		CloseableHttpClient httpclient = HttpClients.custom()
//		        .setDefaultCookieStore(generateCookieStore())
//		        .build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		

		String result = "";

		try {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader(Constant.COOKIE, FileUtil.getProperty(Constant.COOKIE));
			
			CloseableHttpResponse response = httpclient.execute(httpGet);

			try {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			} finally {
				response.close();
			}

		} catch (Exception e) {
			result = "";
		}
		return result;
	}

	public static String generateUri(String baseUri, Map<String, String> params) {
		String urlParams = "";
		String url = baseUri;

		if (null != params) {
			for (Entry<String, String> entry : params.entrySet()) {
				urlParams += entry.getKey() + "=" + entry.getValue() + "&";
			}
			urlParams = urlParams.substring(0, urlParams.length() - 1);
		}

		if (baseUri.contains(Constant.QUESTION_MARK)) {
			url = url + "&" + urlParams;
		} else {
			url = url + Constant.QUESTION_MARK + urlParams;
		}
		return url;
	}

}
