package io.project.edoctor.controller;

import io.project.edoctor.model.symptom.Symptom;
import io.project.edoctor.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SymptomController {

    @Autowired
    private SymptomService symptomService;


    @GetMapping("/symptom/{id}")
    public Symptom getSymptom(@PathVariable("id") String id){

        return symptomService.getSymptomById(id);
        
    }
}
