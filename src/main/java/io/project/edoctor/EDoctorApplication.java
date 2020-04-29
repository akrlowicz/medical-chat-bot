package io.project.edoctor;

import io.project.edoctor.model.Condition;
import io.project.edoctor.model.DiagnosisRequest;
import io.project.edoctor.model.Evidence;
import io.project.edoctor.service.DiagnosisService;
import io.project.edoctor.service.ParseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootApplication
public class EDoctorApplication {

    private static ParseService parseService;
    private static DiagnosisService diagnosisService;
    private static DiagnosisRequest diagnosisRequest;
    private static String sex;


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Condition> conditions;
        List<String> commonResponse = new ArrayList<>();
        Map<String, String> map = new HashMap<String, String>();
        parseService = new ParseService();
        diagnosisService = new DiagnosisService();
        String id;
        Evidence evidence;

        System.out.println("Welcome to e-doctor app!") ;
        System.out.println("What is your gender? (f/m)");
        String s = scanner.nextLine();
        if (s == "f"){sex= "female"; }
        else{sex= "male"; }

        System.out.println("How old are you?");
        s= br.readLine();
        int age = Integer.parseInt(s);

        System.out.println("Please write down your symptoms, and we'll try to help you with your preliminary diagnosis.");
        br = new BufferedReader(new InputStreamReader(System.in));
        String mentions= br.readLine();
        map.put("text", mentions);

        List mentionList = parseService.getParsed(map).getMentions();
        List<String> idList = new ArrayList<>();
        for (int i=0;i<mentionList.size();i++){
            idList.add(parseService.getParsed(map).getMentions().get(i).getId());
        }

        List<Evidence> evidences = new ArrayList<Evidence>();
        for (int i=0;i<idList.size();i++){
            evidence = new Evidence(idList.get(i),"present");
            evidences.add(evidence);
        }

        //do tego momentu wszystko jest w funkcjach w klasie SimpleChat,

        SpringApplication.run(EDoctorApplication.class, args);


        diagnosisRequest = new DiagnosisRequest(sex,age,evidences);
        conditions = diagnosisService.getDiagnosisResponse(diagnosisRequest).getConditions();
        String condition;

        for (int i=0;i<conditions.size();i++){
            condition = conditions.get(i).getCommonName();
            commonResponse.add(condition);
        }
        System.out.println("Your illness is most likely:");
        System.out.println(Arrays.asList(commonResponse).toString());


    }

}
