package com.augmentum.weibo.dao;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.augmentum.weibo.dao.exception.MongoDBException;
import com.augmentum.weibo.util.Constant;
import com.augmentum.weibo.util.FileUtil;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBUtil {
	private static Logger logger = Logger.getLogger(MongoDBUtil.class);
    private static final String DB_NAME = FileUtil.getProperty(Constant.MONGODB_NAME);

    private static Mongo mongo = null;
    private static DB db = null;

    public static void getMongo() throws MongoDBException {
        String host = FileUtil.getProperty(Constant.MONGODB_HOST);
        String port = FileUtil.getProperty(Constant.MONGODB_PORT);
        int serverPort = Integer.valueOf(port);
        try {
            mongo = new Mongo(host, serverPort);
        } catch (UnknownHostException e) {
            throw new MongoDBException(e);
        } catch (MongoException e) {
        	throw new MongoDBException(e);
        }
    }

    public static DBCollection getDBCollection(String collectionName)
            throws MongoDBException {
        if(db == null) {
            db = getDB(DB_NAME);
        }
        return db.getCollection(collectionName);
    }

    public static DB getDB(String dbName) throws MongoDBException {
        if (mongo == null) {
            getMongo();
        }
        DB db = mongo.getDB(dbName);
        
        String userName = FileUtil.getProperty(Constant.MONGODB_USERNAME);
        String password = FileUtil.getProperty(Constant.MONGODB_PASSWORD);
        boolean auth = db.authenticate(userName, password.toCharArray());
        if(!auth) {
        	logger.error("username " + userName + " password " + password);
            throw new MongoDBException("MongoDB Wrong userName or password!");
        }
        return db;
    }

    public static void closeMongo() {
        mongo.close();
    }

}

