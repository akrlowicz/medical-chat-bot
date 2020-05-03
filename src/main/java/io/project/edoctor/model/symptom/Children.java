package io.project.edoctor.model.symptom;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Children {

    private String id;

    @JsonProperty("parent_relation")
    private String parentRelation;

    public Children() {
    }

    public Children(String id, String parentRelation) {
        this.id = id;
        this.parentRelation = parentRelation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentRelation() {
        return parentRelation;
    }

    public void setParentRelation(String parentRelation) {
        this.parentRelation = parentRelation;
    }
}
