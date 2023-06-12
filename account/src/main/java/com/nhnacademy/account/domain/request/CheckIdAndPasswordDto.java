package com.nhnacademy.account.domain.request;


import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CheckIdAndPasswordDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
