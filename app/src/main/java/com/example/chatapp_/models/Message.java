package com.example.chatapp_.models;

public class Message {

    private String mText;
    private String mSenderId;

    public Message() {
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getSenderId() {
        return mSenderId;
    }

    public void setSenderId(String senderId) {
        this.mSenderId = senderId;
    }
}
