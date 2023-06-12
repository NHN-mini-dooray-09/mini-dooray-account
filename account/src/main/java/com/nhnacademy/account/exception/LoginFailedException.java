package com.nhnacademy.account.exception;
public class LoginFailedException extends RuntimeException{
    public LoginFailedException(){
        super("아이디 패스워드를 다시 확인해주세요.");
    }
}
