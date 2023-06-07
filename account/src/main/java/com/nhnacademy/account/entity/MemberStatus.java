package com.nhnacademy.account.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Member_Status")
public class MemberStatus {
    @EmbeddedId
    private Pk pk;

    @Column(name = "access_date")
    private LocalDateTime date;

    @MapsId("memberSeq")
    @ManyToOne
    @JoinColumn(name = "member_seq")
    private Member member;

    @MapsId("statusSeq")
    @ManyToOne
    @JoinColumn(name = "status_seq")
    private Status status;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    @Embeddable
    public static class Pk implements Serializable{
        @Column(name = "member_seq")
        private Long memberSeq;

        @Column(name = "status_seq")
        private Long statusSeq;
    }

}
