package io.project.edoctor.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class UserDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double probability;

    @ManyToOne
    @JoinColumn(name = "interview_id", referencedColumnName = "id")
    private UserInterview interview;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public UserInterview getInterview() {
        return interview;
    }

    public void setInterview(UserInterview interview) {
        this.interview = interview;
    }
}
