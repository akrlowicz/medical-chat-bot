package io.project.edoctor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DiagnosisResponse {

    private Question question;

    private List<Condition> conditions;

    @JsonProperty("should_stop")
    private boolean shouldStop;

    private Object extras;

    public DiagnosisResponse() {
    }

    public DiagnosisResponse(Question question, List<Condition> conditions, Object extras) {
        this.question = question;
        this.conditions = conditions;
        this.extras = extras;
    }

    public DiagnosisResponse(Question question, List<Condition> conditions, boolean shouldStop, Object extras) {
        this.question = question;
        this.conditions = conditions;
        this.shouldStop = shouldStop;
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

    public boolean isShouldStop() {
        return shouldStop;
    }

    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }

    public Object getExtras() {
        return extras;
    }

    public void setExtras(Object extras) {
        this.extras = extras;
    }
}
