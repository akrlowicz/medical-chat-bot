package io.project.edoctor.controller;

import io.project.edoctor.model.Parse;
import io.project.edoctor.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ParseController {

    @Autowired
    private ParseService parseService;

    @PostMapping("/parse")
    public ResponseEntity<Parse> parse(@RequestBody Map<String, String> text) {
        Parse parse = parseService.getParsed(text);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(parse);
    }
}
