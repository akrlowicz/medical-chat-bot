package io.project.edoctor.model;

public class Condition {

    private String id;
    private String name;
    private String common_name;
    private double probability;


    public Condition(String id, String name, String common_name, double probability) {
        this.id = id;
        this.name = name;
        this.common_name = common_name;
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

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
