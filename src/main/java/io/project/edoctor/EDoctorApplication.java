package io.project.edoctor;

import io.project.edoctor.model.diagnosis.Condition;
import io.project.edoctor.model.diagnosis.DiagnosisRequest;
import io.project.edoctor.model.diagnosis.DiagnosisResponse;
import io.project.edoctor.model.diagnosis.Question;
import io.project.edoctor.model.parse.Evidence;
import io.project.edoctor.model.parse.Mention;
import io.project.edoctor.model.parse.Parse;
import io.project.edoctor.service.DiagnosisService;
import io.project.edoctor.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootApplication
public class EDoctorApplication  {



    public static void main(String[] args) throws IOException {
        SpringApplication.run(EDoctorApplication.class, args);

    }

}
