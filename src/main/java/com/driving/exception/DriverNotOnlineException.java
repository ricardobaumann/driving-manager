package com.driving.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DriverNotOnlineException extends RuntimeException {
    public DriverNotOnlineException() {
        super("Driver is not online");
    }
}
