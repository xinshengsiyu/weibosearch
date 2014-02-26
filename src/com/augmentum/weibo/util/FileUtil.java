package com.augmentum.weibo.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import com.augmentum.weibo.exception.WeiboException;

public class FileUtil {

    private static Properties properties = new Properties();

    static {
        InputStream inStream = FileUtil.class.getResourceAsStream(Constant.CONF_PATH);
        try {
            properties.load(inStream);
        } catch (IOException e) {
        	throw new WeiboException("can't find property file");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, Object... other) {
        String url = properties.getProperty(key);
        return MessageFormat.format(url, other);
    }
}
