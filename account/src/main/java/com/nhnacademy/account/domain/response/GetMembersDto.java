package com.nhnacademy.account.domain.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetMembersDto {

    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String status;

}
