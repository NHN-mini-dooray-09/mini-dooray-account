package com.nhnacademy.account.domain.request;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CheckIdAndPasswordDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
