package com.nhnacademy.account.domain;

import lombok.Data;

@Data
public class LoginDto {

    private String id;
    private String password;

    private String email;
}
