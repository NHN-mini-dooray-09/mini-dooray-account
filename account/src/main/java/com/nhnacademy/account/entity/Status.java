package com.nhnacademy.account.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "Status")
public class Status {

    @Id
    @Column(name = "status_seq")
    private Long statusSeq;

    @Column(name = "status_name")
    private String statusName;

    @OneToMany(mappedBy = "status")
    private List<MemberStatus> statuses;
}
