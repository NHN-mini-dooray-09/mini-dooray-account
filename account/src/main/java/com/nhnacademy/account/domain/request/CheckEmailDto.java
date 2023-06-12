package com.nhnacademy.account.domain.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CheckEmailDto {


    @NotBlank
    private String email;
}
