package com.nhnacademy.account.domain.response;


import com.nhnacademy.account.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberDto {

    private Long memberSeq;

    private String id;
    private String password;
    private String email;
    private String name;
    private String statusName;
    private LocalDateTime time;


    @Builder
    public MemberDto(Long memberSeq,String id,String password,String email,String name,String statusName,LocalDateTime time) {
        this.memberSeq=memberSeq;
        this.id=id;
        this.password=password;
        this.email=email;
        this.name=name;
        this.statusName=statusName;
        this.time=time;
    }


}
