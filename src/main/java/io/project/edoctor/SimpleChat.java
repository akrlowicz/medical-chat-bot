package io.project.edoctor;

import io.project.edoctor.model.diagnosis.*;
import io.project.edoctor.model.parse.Evidence;
import io.project.edoctor.model.parse.Mention;
import io.project.edoctor.model.parse.Parse;
import io.project.edoctor.service.DiagnosisService;
import io.project.edoctor.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class SimpleChat implements CommandLineRunner {

    @Autowired
    ParseService parseService;

    @Autowired
    DiagnosisService diagnosisService;

    private Scanner scanner = new Scanner(System.in);
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //dla czytania tekstu


    public String sex() throws IOException {
        System.out.println("What is your gender? (f/m)");
        String sex = scanner.nextLine();
        if (sex.equals("f"))
            return "female";
        else
            return "male";
    }

    public int age() throws IOException {

        System.out.println("How old are you?");

        return scanner.nextInt();

    }

    public Map getMentionsMap() throws IOException {

        String mentions = br.readLine();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", mentions);

        return map;
    }


    public List<Evidence> addEvidences(Map<String, String> map) throws IOException {

        Parse parse = parseService.getParsed(map);

        List<Mention> mentionList = parse.getMentions();
        List<Evidence> evidences = new ArrayList<>();

        if (parse.isObvious()) {

            for (Mention m : mentionList)
                evidences.add(new Evidence(m.getId(), m.getChoiceId()));

        } else {
            for (Mention m : mentionList) {

                System.out.println("Did you mean "
                        + m.getChoiceId() + " "
                        + m.getCommonName().toLowerCase() + "? y/n");


                if (br.readLine().equals("y"))
                    evidences.add(new Evidence(m.getId(), m.getChoiceId()));
            }
        }

        return evidences;
    }

    public void listItems(Question question) {
        System.out.println(question.getText());

        for (Item item: question.getItems()) {
            for (Choices choice: item.getChoices())
                System.out.print(choice.getLabel() + "/");
                System.out.println();

        }
    }


    @Override
    public void run(String... args) throws Exception {


        System.out.println("Welcome to e-doctor app!");

        String sex = sex();
        int age = age();

        System.out.println("Please write down your symptoms, and we'll try to help you with your preliminary diagnosis.");

        List<Evidence> evidences;
        Map<String,String> map = getMentionsMap();
        evidences = addEvidences(map);

        //add first symptom as inital: niezbedne do dalszej diagnozy i uzyskania warunku should_stop

        if (!evidences.isEmpty())
            evidences.get(0).setInitial(true);

        //disable groups w kontekscie pytan
        Map<String, Boolean> disableGroups = new HashMap<>();
        disableGroups.put("disable_groups", true);

        // wyslij do diagnozy
        DiagnosisResponse diagnosisResponse = diagnosisService.getDiagnosisResponse(new DiagnosisRequest(sex, age, evidences, disableGroups));

        //interview flow ma trwac dopoki bot nie ma wystarczajaco informacji
        while (!diagnosisResponse.getShouldStop()) {

            System.out.println("What else would you like to report?");

            String furtherMentions = br.readLine();

            if (furtherMentions.equals("nothing")) {

                while(!diagnosisResponse.getShouldStop()) {

                    diagnosisResponse = diagnosisService.getDiagnosisResponse(new DiagnosisRequest(sex, age, evidences, disableGroups));

                    Question question = diagnosisResponse.getQuestion();

                    listItems(question); //null pointer

                    String usersChoice = br.readLine();

                    for (Item item : question.getItems()) {
                        for (Choices choice : item.getChoices()) {
                            if (usersChoice.equalsIgnoreCase(choice.getLabel()))

                                evidences.add(new Evidence(item.getId(), choice.getId()));
                        }

                    }

                }

            } else {

                map = new HashMap<>();
                map.put("text", furtherMentions);
                addEvidences(map);

                diagnosisResponse = diagnosisService.getDiagnosisResponse(new DiagnosisRequest(sex, age, evidences,disableGroups));

            }

        }
        System.out.println("Your illness is most likely:");

        List<Condition> conditions = diagnosisResponse.getConditions();

        for (Condition c : conditions)
            System.out.println(c.getCommonName() + ", probability: " + c.getProbability());


        scanner.close();
        br.close();


    }
}
