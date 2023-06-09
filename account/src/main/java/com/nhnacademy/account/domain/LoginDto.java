package com.nhnacademy.account.domain;

import lombok.Data;

@Data
public class LoginDto {

    private String id;
    private String password;

    private String email;


    public LoginDto(String id, String password) {
        this.id=id;
        this.password=password;
    }
    public LoginDto(String email){
        this.email=email;
    }


}
