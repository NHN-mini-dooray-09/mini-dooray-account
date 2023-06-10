package com.nhnacademy.account.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_seq")
    private Long seq;

    @Column(name = "member_id",unique = true)
    private String id;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_name")
    private String name;


    @Column(name = "status")
    private String statusName;

    @Column(name = "dateTime")
    private LocalDateTime time;

    public Member(){

    }

    public Member(Long seq,String id,String email,String password,String name,String statusName,LocalDateTime time){
        this.seq=seq;
        this.id=id;
        this.email=email;
        this.password=password;
        this.name=name;
        this.statusName=statusName;
        this.time=time;
    }

}
