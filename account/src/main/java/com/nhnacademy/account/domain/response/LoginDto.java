package com.nhnacademy.account.domain.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

}
