package io.project.edoctor.controller;

import io.project.edoctor.model.Symptom;
import io.project.edoctor.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymptomController {

    @Autowired
    private SymptomService symptomService;


    @PostMapping("/symptom/{id}")
    public Symptom getSymptom(@RequestParam String id){

        return symptomService.getSymptomById(id);
        
    }
}
