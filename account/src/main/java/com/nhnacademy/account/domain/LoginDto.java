package com.nhnacademy.account.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class LoginDto {

    private String id;
    private String password;

    private String email;

    private LocalDate date;


    public LoginDto(String id, String password) {
        this.id=id;
        this.password=password;
    }
    public LoginDto(String email){
        this.email=email;
    }


}
