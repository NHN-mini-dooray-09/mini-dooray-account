package com.nhnacademy.account.entity;

import lombok.*;

import javax.persistence.*;
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

    @Column(name = "member_id")
    private String id;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_name")
    private String name;

    @OneToMany(mappedBy = "member")
    private List<MemberAuthority> authority;

    @OneToMany(mappedBy = "member")
    private List<MemberStatus> statuses;

    public Member(){

    }

    public Member(Long seq,String id,String email,String password,String name){
        this.seq=seq;
        this.id=id;
        this.email=email;
        this.password=password;
        this.name=name;
    }

}
