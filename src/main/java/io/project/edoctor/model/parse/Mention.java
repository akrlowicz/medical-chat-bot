package io.project.edoctor.model.parse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mention {

    private String id;

    private String name;

    @JsonProperty("common_name")
    private String commonName;

    private String orth;

    @JsonProperty("choice_id")
    private String choiceId;

    private String type; //symptom ex.

    public Mention() {
    }

    public Mention(String id, String name, String commonName, String orth, String choiceId, String type) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.orth = orth;
        this.choiceId = choiceId;
        this.type = type;
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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOrth() {
        return orth;
    }

    public void setOrth(String orth) {
        this.orth = orth;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Mention{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", commonName='" + commonName + '\'' +
                ", orth='" + orth + '\'' +
                ", choiceId='" + choiceId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
