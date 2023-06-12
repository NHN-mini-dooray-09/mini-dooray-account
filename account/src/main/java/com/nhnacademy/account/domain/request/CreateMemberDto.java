package com.nhnacademy.account.domain.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Builder
public class CreateMemberDto {

    @NotBlank
    private String id;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String status;

    @NotBlank
    private String role;
    private LocalDateTime time;

}
