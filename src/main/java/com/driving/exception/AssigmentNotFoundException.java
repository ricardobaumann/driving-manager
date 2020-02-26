package com.driving.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssigmentNotFoundException extends RuntimeException {
    public AssigmentNotFoundException(Long id) {
        super(String.format("Assigment %d not found or already finished", id));
    }
}
