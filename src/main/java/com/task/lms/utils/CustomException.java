package com.task.lms.utils;

public class CustomException extends RuntimeException {
    public CustomException(String errorMessage){
        super(errorMessage);
    }
}