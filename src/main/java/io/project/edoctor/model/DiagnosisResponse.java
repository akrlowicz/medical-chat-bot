package io.project.edoctor.model;

import java.util.List;

public class DiagnosisResponse {

    private Question question;
    private List<Condition> conditions;
    private boolean should_stop;
    private String extras;

    public DiagnosisResponse(Question question, List<Condition> conditions, boolean should_stop, String extras) {
        this.question = question;
        this.conditions = conditions;
        this.should_stop = should_stop;
        this.extras = extras;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public boolean isShould_stop() {
        return should_stop;
    }

    public void setShould_stop(boolean should_stop) {
        this.should_stop = should_stop;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
}
