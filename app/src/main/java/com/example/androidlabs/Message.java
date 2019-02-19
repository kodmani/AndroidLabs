package com.example.androidlabs;

public class Message {

    private String text;
    protected long id;
    private boolean isSend;

    public Message(Long id, String text, boolean isSend) {
        this.id = id;
        this.text = text;
        this.isSend = isSend;
    }

    public Message(String text, boolean isSend) {
        this.text = text;
        this.isSend = isSend;
    }

    public Message(String text, long id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public boolean isSend() {
        return isSend;
    }

    public long getId() {
        return id;
    }
}