package com.nhnacademy.account.domain.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CurrentStatusDto {

    @NotBlank
    private String name;

    @NotBlank
    private String status;

    @Builder
    public CurrentStatusDto(String name,String status){
        this.name=name;
        this.status=status;
    }
}
