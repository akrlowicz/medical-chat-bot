package io.project.edoctor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Condition {

    private String id;

    private String name;

    @JsonProperty("common_name")
    private String commonName;

    private double probability;

    public Condition() {
    }

    public Condition(String id, String name, String commonName, double probability) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.probability = probability;
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

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
