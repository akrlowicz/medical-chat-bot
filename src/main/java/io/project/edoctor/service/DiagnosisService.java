package io.project.edoctor.service;

import io.project.edoctor.model.diagnosis.DiagnosisRequest;
import io.project.edoctor.model.diagnosis.DiagnosisResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DiagnosisService {

    private String url = "https://api.infermedica.com/v2/diagnosis";
    private final RestTemplate restTemplate = new RestTemplate();


    public DiagnosisResponse getDiagnosisResponse(DiagnosisRequest diagnosisRequest) {

        // create custom headers with api key
        HttpHeaders headers = new HttpHeaders();

        headers.set("App-Id","19963157");
        headers.set("App-Key","f3ad821855191f2c852f1efa79d1456f");

        // set content type of body and type to accept
        headers.setContentType(MediaType.APPLICATION_JSON);

        // build the request
        HttpEntity<DiagnosisRequest> request = new HttpEntity<>(diagnosisRequest,headers);

        ResponseEntity<DiagnosisResponse> response = this.restTemplate.postForEntity(url, request, DiagnosisResponse.class);

        // check response status code
        if (response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            return null;


        // alternatively
        //DiagnosisResponse response = this.restTemplate.postForObject(url, request, DiagnosisResponse.class);
        //return response;
    }
}
