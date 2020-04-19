package io.project.edoctor.model;

import java.util.List;

public class Item {
    //question item

    private String id;
    private String name;
    private List<Choices> choices;

    public Item(String id, String name, List<Choices> choices) {
        this.id = id;
        this.name = name;
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }
}
