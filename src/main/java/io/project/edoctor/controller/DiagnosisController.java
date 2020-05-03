package io.project.edoctor.controller;

import io.project.edoctor.model.diagnosis.DiagnosisRequest;
import io.project.edoctor.model.diagnosis.DiagnosisResponse;
import io.project.edoctor.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;


    @PostMapping("/diagnosis")
    public DiagnosisResponse diagnosis(@RequestBody DiagnosisRequest diagnosisRequest) {

        return diagnosisService.getDiagnosisResponse(diagnosisRequest);
    }
}
