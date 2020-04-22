package io.project.edoctor.service;

import io.project.edoctor.model.Symptom;
import org.apache.coyote.Response;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class SymptomService {

    private String url = "https://api.infermedica.com/v2/symptoms/{id}";
    private RestTemplate restTemplate = new RestTemplate();

    public Symptom getSymptomById(String id) {

        // create custom headers with api key
        HttpHeaders headers = new HttpHeaders();

        headers.set("App-Id","19963157");
        headers.set("App-Key","f3ad821855191f2c852f1efa79d1456f");


        HttpEntity<Symptom> request = new HttpEntity<>(headers);

        // http request with custom header and parameter
        ResponseEntity<Symptom> response = this.restTemplate.exchange(url, HttpMethod.GET, request, Symptom.class, id);

        if (response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            return null;
    }

}
