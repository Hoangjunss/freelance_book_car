package com.freelance.bookCar.exception;

import lombok.Getter;

@Getter
public class CloudinaryException extends RuntimeException{
    private final java.lang.Error error;

    public CloudinaryException(java.lang.Error error) {
        super(error.getMessage());
        this.error = error;
    }

}
