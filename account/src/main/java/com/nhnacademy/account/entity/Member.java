package com.nhnacademy.account.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long memberSeq;

    @Column(name = "member_id",unique = true)
    private String id;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_name")
    private String name;


    @Column(name = "status")
    private String status;

    @Column(name = "authority")
    private String role;

    @Column(name = "dateTime")
    private LocalDateTime time;

    @Builder
    public Member(Long memberSeq,String id,String password,String email,String name,String status,LocalDateTime time,String role){
        this.memberSeq=memberSeq;
        this.id=id;
        this.password=password;
        this.email=email;
        this.name=name;
        this.status=status;
        this.time=time;
        this.role=role;
    }

    public void updateMember(String name,String status){
        this.name=name;
        this.status=status;
    }

}
