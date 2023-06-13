package com.nhnacademy.account.repository;

import com.nhnacademy.account.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByIdAndPassword() {
        Member member=Member.builder().build();
        memberRepository.save(member);
        Assertions.assertThat(member.getMemberSeq()).isNull();
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findById() {
    }
}