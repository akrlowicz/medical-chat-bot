package io.project.edoctor.service;


import io.project.edoctor.model.diagnosis.*;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserDiagnosis;
import io.project.edoctor.model.entity.UserInterview;
import io.project.edoctor.model.parse.Evidence;
import io.project.edoctor.model.parse.Mention;
import io.project.edoctor.model.parse.Parse;
import io.project.edoctor.repository.UserDiagnosisRepository;
import io.project.edoctor.repository.UserInterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class InterviewService {
    @Autowired
    ParseService parseService;

    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    UserInterviewRepository userInterviewRepository;

    @Autowired
    UserDiagnosisRepository userDiagnosisRepository;

    private User user;
    private String sex;
    private int age;

    private List<Evidence> evidences;
    private List<Mention> mentionList;

    private Map<String, Boolean> disableGroups = new HashMap<>();
    private DiagnosisResponse diagnosisResponse;
    private Question question;
    private List<String> possibleAnswers;

    private boolean isItFirstRequest;
    private boolean isItFirstTextMessage;
    private boolean isItYesNoQuestion;
    private boolean isItQuestionTime;
    private boolean isInterviewFinished;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEvidences(List<Evidence> evidences) {
        this.evidences = evidences;
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

    public boolean isItYesNoQuestion() {
        return isItYesNoQuestion;
    }

    public void setItYesNoQuestion(boolean itYesNoQuestion) {
        isItYesNoQuestion = itYesNoQuestion;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
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
            if (!mentionList.isEmpty()) {
                Mention m = mentionList.get(0);
                isItYesNoQuestion = true;
                possibleAnswers.clear();
                possibleAnswers.add("Yes");
                possibleAnswers.add("No");
                return "Did you mean "
                        + m.getChoiceId() + " "
                        + m.getCommonName().toLowerCase() + "?";
            }
            else  {
                isItFirstTextMessage = true;
                return "Unfortunately, your symptoms are incomprehensible. Try to write what is wrong with other words.";
            }
        }
    }

    private String diagnosisResponse() {
        if (isItFirstRequest) {
            isItFirstRequest = false;
            sex = user.getUserData().getGender().toLowerCase();
            age = user.getUserData().getAge();
            disableGroups.clear();
            disableGroups.put("disable_groups", true);

            if (!evidences.isEmpty())
                evidences.get(0).setInitial(true);
        }

        diagnosisResponse = diagnosisService.getDiagnosisResponse(new DiagnosisRequest(sex, age, evidences, disableGroups));

        if (!diagnosisResponse.getShouldStop()) {

            if (!isItQuestionTime) {
                isItYesNoQuestion = false;
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

        possibleAnswers.clear();
        for (Item item: question.getItems()) {
            for (Choices choice: item.getChoices()) {
                possibleAnswers.add(choice.getLabel());
            }
        }

        return question.getText();
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

        UserInterview userInterview = new UserInterview();
        userInterview.setDate(LocalDate.now());
        userInterview.setUser(user);
        //List<UserDiagnosis> userDiagnoses = new ArrayList<>();
        //userInterview.setDiagnoses(userDiagnoses);

        userInterviewRepository.save(userInterview);
        stringBuilder.append("Your illness is most likely:");
        List<Condition> conditions = diagnosisResponse.getConditions();

        for (Condition c : conditions) {
            stringBuilder.append("\n" + c.getCommonName() + ", probability: " + c.getProbability());

            UserDiagnosis d = new UserDiagnosis();
            d.setName(c.getCommonName());
            d.setProbability(c.getProbability());
            d.setInterview(userInterview);

            userDiagnosisRepository.save(d);
            //userDiagnoses.add(d);
        }
        //userInterview.setUserDiagnoses(userDiagnoses);

        //userInterview.setDiagnoses(userDiagnoses);
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

    public UserInterview findById(Integer id) throws Exception {
        return userInterviewRepository.findById(id).orElseThrow(Exception::new);
    }
}
