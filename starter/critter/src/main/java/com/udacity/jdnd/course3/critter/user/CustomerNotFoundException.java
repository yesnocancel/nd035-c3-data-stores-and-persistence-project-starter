package com.udacity.jdnd.course3.critter.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        this("Customer not found");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}