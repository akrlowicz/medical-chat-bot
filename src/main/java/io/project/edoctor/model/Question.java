package io.project.edoctor.model;

import java.util.List;

public class Question {

    private String type; //single/group_single/gorup_multiple -> further note: we can disable group questions

    private String text;

    private List<Item> items;

    private Object extras;

    public Question() {
    }

    public Question(String type, String text, List<Item> items, Object extras) {
        this.type = type;
        this.text = text;
        this.items = items;
        this.extras = extras;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Object getExtras() {
        return extras;
    }

    public void setExtras(Object extras) {
        this.extras = extras;
    }
}
