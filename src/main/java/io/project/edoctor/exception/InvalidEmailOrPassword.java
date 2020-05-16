package io.project.edoctor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidEmailOrPassword extends RuntimeException {

    public InvalidEmailOrPassword(String message) {
        super(message);
    }
}
