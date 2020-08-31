package com.example.chatapp_.models;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {

    private String mId;
    private List<String> mUserIds;

    public Chat() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public List<String> getUserIds() {
        return mUserIds;
    }

    public void setUserIds(List<String> userIds) {
        this.mUserIds = userIds;
    }
}
