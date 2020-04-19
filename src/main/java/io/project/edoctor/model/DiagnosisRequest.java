package io.project.edoctor.model;

import java.util.List;

public class DiagnosisRequest {

    private String sex;
    private int age;
    private List<Evidence> evidence; //list of symptoms, risk factors and test results

    public DiagnosisRequest(String sex, int age, List<Evidence> evidence) {
        this.sex = sex;
        this.age = age;
        this.evidence = evidence;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Evidence> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<Evidence> evidence) {
        this.evidence = evidence;
    }
}
