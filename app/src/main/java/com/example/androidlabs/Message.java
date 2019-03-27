package com.example.androidlabs;

public class Message {

    private String text;
    protected long id;
    private boolean isSend;

    public Message(long id, String text, boolean isSend) {
        this.id = id;
        this.text = text;
        this.isSend = isSend;
    }

    public Message(String text, long id) {
        this.text = text;
        this.id = id;
    }

    public Message(String text, boolean isSend){
        this(0, text, isSend);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void putString(String key, Message value) {
        throw new RuntimeException("Stub!");
    }
}