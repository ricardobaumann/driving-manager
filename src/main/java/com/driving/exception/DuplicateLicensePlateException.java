package com.driving.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateLicensePlateException extends RuntimeException {
    public DuplicateLicensePlateException(@NotNull String licensePlate) {
        super(String.format("License plate %s was already used", licensePlate));
    }
}
