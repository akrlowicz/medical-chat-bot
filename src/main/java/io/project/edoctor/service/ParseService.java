package io.project.edoctor.service;

import io.project.edoctor.model.parse.Parse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ParseService {

    private String url = "https://api.infermedica.com/v2/parse";
    private final RestTemplate restTemplate = new RestTemplate();

    public Parse getParsed(Map<String, String> text) {


        // create custom headers with api key
        HttpHeaders headers = new HttpHeaders();

        headers.set("App-Id","19963157");
        headers.set("App-Key","f3ad821855191f2c852f1efa79d1456f");

        // set content type of body and type to accept
        headers.setContentType(MediaType.APPLICATION_JSON);


        // build the request
        HttpEntity<Map<String, Object>> request = new HttpEntity(text, headers);

        ResponseEntity<Parse> response = this.restTemplate.postForEntity(url, request, Parse.class);

        if (response.getStatusCode() == HttpStatus.OK)
            return response.getBody();
        else
            return null;


    }
}
