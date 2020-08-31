package com.example.chatapp_.models;

import java.io.Serializable;

public class User implements Serializable {

    private String mId;
    private String mName;

    public User(){

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
