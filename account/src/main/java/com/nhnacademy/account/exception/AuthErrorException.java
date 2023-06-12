package com.nhnacademy.account.exception;

public class AuthErrorException extends RuntimeException{
    public AuthErrorException(String message){
        super(message);
    }
}
