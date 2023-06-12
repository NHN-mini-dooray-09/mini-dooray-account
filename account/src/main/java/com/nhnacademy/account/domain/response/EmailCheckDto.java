package com.nhnacademy.account.domain.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
