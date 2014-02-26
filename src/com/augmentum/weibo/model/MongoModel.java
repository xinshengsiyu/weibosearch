package com.augmentum.weibo.model;

import java.io.Serializable;
import java.util.Date;

public abstract class MongoModel implements Serializable {

    private static final long serialVersionUID = 4328716674588255208L;

    protected String id;
    protected String collectionName;

    public String getCollectionName() {
        return collectionName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    protected long getTime(Date date) {
        return date != null ? date.getTime() : null;
    }
}
