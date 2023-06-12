package com.nhnacademy.account.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedStatusDto {

    @NotBlank
    private String name;

    @NotBlank
    private String status;
}
