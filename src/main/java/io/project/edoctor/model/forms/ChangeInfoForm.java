package io.project.edoctor.model.forms;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ChangeInfoForm {


    private String name;

    @Max(220)
    @Min(140)
    private Integer height;

    @Max(150)
    @Min(40)
    private Integer weight;

    public ChangeInfoForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
