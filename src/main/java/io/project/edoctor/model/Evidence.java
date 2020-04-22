package io.project.edoctor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Evidence {

    private String id; //symptom, risk factor or lab test

    @JsonProperty("choice_id")
    private String choiceId; //present/absent

    private boolean initial;  //at least one true

    public Evidence() {
    }

    public Evidence(String id, String choiceId) {
        this.id = id;
        this.choiceId = choiceId;
    }

    public Evidence(String id, String choiceId, boolean initial) {
        this.id = id;
        this.choiceId = choiceId;
        this.initial = initial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }
}
