package io.project.edoctor;

import io.project.edoctor.model.Evidence;
import io.project.edoctor.model.Mention;
import io.project.edoctor.service.ParseService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SimpleChat {

    public SimpleChat() {
    }

    public void hello() {
        System.out.println("Welcome to e-doctor app!") ;
    }

    public String sex() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your gender? (f/m)");
        String sex = scanner.nextLine();
        if (sex == "f"){ return "female"; }
        else{ return "male"; }
    }

    public int age() throws IOException {
        System.out.println("How old are you?");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s= br.readLine();
        int age = Integer.parseInt(s);
        return age;
    }
    public Map getMentionsMap() throws IOException {
        System.out.println("Please write down your symptoms, and we'll try to help you with your preliminary diagnosis.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String mentions= br.readLine();
        Map<String, String> map = new HashMap<String, String>();
        map.put("text", mentions);
        return map;
    }

      public List<String> makeIdList(ParseService parseService, Map map){
        parseService = new ParseService();
        List mentionList = parseService.getParsed(map).getMentions();
        List<String> idList = null;
        for (int i=0;i<mentionList.size();i++){
            idList.add(parseService.getParsed(map).getMentions().get(i).getId());
        }
        return idList;
    }

    public List<Evidence> makeEvidenceList(List<String> idList){
        String id;
        Evidence evidence;
        List<Evidence> evidences = new ArrayList<Evidence>();
        for (int i=0;i<idList.size();i++){
            evidence = new Evidence(idList.get(i),"present");
            evidences.add(evidence);
        }
        return evidences;
    }



}
