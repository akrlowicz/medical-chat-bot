package io.project.edoctor.service;


import io.project.edoctor.model.diagnosis.*;
import io.project.edoctor.model.parse.Evidence;
import io.project.edoctor.model.parse.Mention;
import io.project.edoctor.model.parse.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InterviewService {
    @Autowired
    ParseService parseService;

    @Autowired
    DiagnosisService diagnosisService;

    private String sex;
    private int age;

    private List<Evidence> evidences;
    private List<Mention> mentionList;

    private Map<String, Boolean> disableGroups = new HashMap<>();
    private DiagnosisResponse diagnosisResponse;
    private Question question;

    private boolean isItFirstRequest;
    private boolean isItFirstTextMessage;
    private boolean isItQuestionTime;
    private boolean isInterviewFinished;

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

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
    }

    public List<Mention> getMentionList() {
        return mentionList;
    }

    public void setMentionList(List<Mention> mentionList) {
        this.mentionList = mentionList;
    }

    public boolean isItFirstRequest() {
        return isItFirstRequest;
    }

    public void setItFirstRequest(boolean itFirstRequest) {
        isItFirstRequest = itFirstRequest;
    }

    public boolean isItFirstTextMessage() {
        return isItFirstTextMessage;
    }

    public void setItFirstTextMessage(boolean itFirstTextMessage) {
        isItFirstTextMessage = itFirstTextMessage;
    }

    public boolean isItQuestionTime() {
        return isItQuestionTime;
    }

    public void setItQuestionTime(boolean itQuestionTime) {
        isItQuestionTime = itQuestionTime;
    }

    public boolean isInterviewFinished() {
        return isInterviewFinished;
    }

    public void setInterviewFinished(boolean interviewFinished) {
        isInterviewFinished = interviewFinished;
    }

    private Map getMentionsMap(String mentions) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("text", mentions);

        return map;
    }

    private String handleFirstTextMessage(String userMessage) {

        Map<String,String> map = getMentionsMap(userMessage);
        Parse parse = parseService.getParsed(map);
        mentionList = parse.getMentions();

        if (parse.isObvious()) {

            for (Mention m : mentionList)
                evidences.add(new Evidence(m.getId(), m.getChoiceId()));

            mentionList.clear();

            return diagnosisResponse();

        } else {
            Mention m = mentionList.get(0);
            return "Did you mean "
                    + m.getChoiceId() + " "
                    + m.getCommonName().toLowerCase() + "? Yes/No";
        }
    }

    private String diagnosisResponse() {
        if (isItFirstRequest) {
            isItFirstRequest = false;
            disableGroups.clear();
            disableGroups.put("disable_groups", true);

            if (!evidences.isEmpty())
                evidences.get(0).setInitial(true);
        }

        diagnosisResponse = diagnosisService.getDiagnosisResponse(new DiagnosisRequest(sex, age, evidences, disableGroups));

        if (!diagnosisResponse.getShouldStop()) {

            if (!isItQuestionTime) {
                isItFirstTextMessage = true;
                return "What else would you like to report? Type 'nothing' if you do not know.";
            }
            else {
                question = diagnosisResponse.getQuestion();
                return printQuestion();
            }
        }
        else {
            isInterviewFinished = true;
            return getFinalDiagnosis();
        }
    }

    private String printQuestion() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(question.getText() + " ");

        for (Item item: question.getItems()) {
            for (Choices choice: item.getChoices()) {
                stringBuilder.append(choice.getLabel() + "/");
            }
        }

        return stringBuilder.toString();
    }

    private String reactForAnswerForAQuestion(String userMessage) {
        for (Item item : question.getItems()) {
            for (Choices choice : item.getChoices()) {
                if (userMessage.equalsIgnoreCase(choice.getLabel()))
                    evidences.add(new Evidence(item.getId(), choice.getId()));
            }
        }

        return diagnosisResponse();
    }

    private String getFinalDiagnosis() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Your illness is most likely:");

        List<Condition> conditions = diagnosisResponse.getConditions();

        for (Condition c : conditions)
            stringBuilder.append("\n" + c.getCommonName() + ", probability: " + c.getProbability());

        return stringBuilder.toString();
    }

    public String getBotAnswer (String userMessage) {

        if (isItFirstTextMessage) {
            isItFirstTextMessage = false;

            if(userMessage.toLowerCase().equals("nothing")) {
                isItQuestionTime = true;
                return diagnosisResponse();
            }
            else {
                return handleFirstTextMessage(userMessage);
            }
        }
        else if (!mentionList.isEmpty()) {

            Mention m = mentionList.get(0);
            if (userMessage.toLowerCase().equals("yes"))
                evidences.add(new Evidence(m.getId(), m.getChoiceId()));

            mentionList.remove(0);

            if (!mentionList.isEmpty()) {
                Mention newMention = mentionList.get(0);
                return "Did you mean "
                        + newMention.getChoiceId() + " "
                        + newMention.getCommonName().toLowerCase() + "? Yes/No";
            }
            else {
                return diagnosisResponse();
            }
        }
        else {
            return reactForAnswerForAQuestion(userMessage);
        }
    }
}
