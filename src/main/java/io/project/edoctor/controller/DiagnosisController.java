package io.project.edoctor.controller;

import io.project.edoctor.model.DiagnosisRequest;
import io.project.edoctor.model.DiagnosisResponse;
import io.project.edoctor.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;


    @PostMapping("/diagnosis")
    public ResponseEntity<DiagnosisRequest> diagnosis(@RequestBody DiagnosisRequest diagnosisRequest) {
        DiagnosisResponse diagnosisResponse = diagnosisService.getDiagnosisResponse(diagnosisRequest);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(diagnosisRequest);

    }
}
