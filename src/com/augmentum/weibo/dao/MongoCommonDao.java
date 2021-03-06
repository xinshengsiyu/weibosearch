package com.augmentum.weibo.dao;

import com.augmentum.weibo.dao.exception.MongoDBException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class MongoCommonDao {

    private static MongoCommonDao dao = null;


    private MongoCommonDao() {
    }

    public static MongoCommonDao getInstance() {
        if (dao == null) {
            dao = new MongoCommonDao();
        }
        return dao;
    }
    
    public boolean save(String collectionName, String json) throws MongoDBException{
      DBCollection collection = MongoDBUtil.getDBCollection(collectionName);
      DBObject dbo = (DBObject) JSON.parse(json);
      WriteResult wr = collection.insert(dbo);
      return wr.getError() == "null";
    }
    
    public boolean save(String collectionName, DBObject dbo) throws MongoDBException{
        DBCollection collection = MongoDBUtil.getDBCollection(collectionName);
        WriteResult wr = collection.insert(dbo);
        return wr.getError() == "null";
      }
    
    public DBObject findByAttribute(String collectionName, String columnName, String columnValue) throws MongoDBException{
    	DBCollection collection = MongoDBUtil.getDBCollection(collectionName);
    	DBObject searchObject = new BasicDBObject(columnName, columnValue);
        DBCursor cursor = collection.find(searchObject);
        return cursor.hasNext() ? cursor.next() : null;
    }

}
