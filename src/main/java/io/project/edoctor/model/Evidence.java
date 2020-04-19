package io.project.edoctor.model;

public class Evidence {

    private String id; //symptom, risk factor or lab test
    private String choice_id; //present/absent
    private boolean initial;  //at least one true

    public Evidence(String id, String choice_id, boolean initial) {
        this.id = id;
        this.choice_id = choice_id;
        this.initial = initial;
    }

    public Evidence(String id, String choice_id) {
        this.id = id;
        this.choice_id = choice_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(String choice_id) {
        this.choice_id = choice_id;
    }
}
