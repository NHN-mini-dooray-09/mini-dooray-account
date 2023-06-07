package com.nhnacademy.account.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Member_Authorities")
public class MemberAuthority {

    @Id
    @Column(name = "member_seq")
    private Long memberSeq;

    private String authority;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;
}
