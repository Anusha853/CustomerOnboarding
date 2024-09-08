package com.bootcamp.customer.Onboarding.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
        super(message);
    }

}
