package io.project.edoctor.controller;

public class Message {
    private String sender;
    private String value;

    public Message() {
    }

    public Message(String sender, String value) {
        this.sender = sender;
        this.value = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
