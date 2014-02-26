package com.augmentum.weibo.dao.exception;

public class MongoDBException extends Exception {

    private static final long serialVersionUID = -3343185631718584461L;

    public MongoDBException() {
        super();
    }

    public MongoDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public MongoDBException(String message) {
        super(message);
    }

    public MongoDBException(Throwable cause) {
        super(cause);
    }

}
