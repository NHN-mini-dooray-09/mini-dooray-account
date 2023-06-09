package com.nhnacademy.account.service;

import com.nhnacademy.account.AccountApplication;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Order(1)
    void testFindByIdAndPassword(){
        Member testmember=new Member(1L,"test","test@mail.com","1234","testUser");
        entityManager.merge(testmember);

        Member member=memberRepository.findByIdAndPassword("test","1234");
        Assertions.assertThat(member).isEqualTo(testmember);
    }

    @Test
    @Order(2)
    void findByEmail(){
        Member testmember=new Member(2L,"test","test2@mail.com","1234","testUser");
        entityManager.merge(testmember);

        Member member=memberRepository.findByEmail("test2@mail.com");
        Assertions.assertThat(member).isEqualTo(testmember);
    }
}