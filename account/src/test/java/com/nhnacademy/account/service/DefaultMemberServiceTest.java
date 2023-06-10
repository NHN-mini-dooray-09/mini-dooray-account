package com.nhnacademy.account.service;

import com.nhnacademy.account.AccountApplication;
import com.nhnacademy.account.domain.MemberDto;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
class DefaultMemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Order(1)
    void teatCreateMember(){
        Member testmember=new Member(2L,"test2","test2@mail.com","1234","testUser2","가입",LocalDateTime.now());
        memberRepository.save(testmember);
    }
    @Test
    @Order(2)
    void testGetMembers(){
        List<Member> members=memberService.getMembers();
        Assertions.assertThat(members).hasSize(1);
    }

    @Test
    @Order(3)
    void testCheckExist(){
        String id="test";
        String password="1234";

        boolean exists=memberService.checkExist(id,password,LocalDateTime.now());

        Assertions.assertThat(exists);
    }

    @Test
    @Order(4)
    void testCheckEmail(){
        String email="tset@mail.com";

        boolean exists=memberService.checkEmail(email);

        Assertions.assertThat(exists);

    }

    @Test
    @Order(5)
    void testGetMember(){
        Member member=new Member(1L,"test3","test3@mail.com","1234","testUser3","가입", LocalDateTime.now());
        memberRepository.save(member);

        Member searchMember=memberService.getMember(member.getSeq());

        Assertions.assertThat(searchMember).isNotNull();
        Assertions.assertThat(searchMember.getSeq()).isEqualTo(member.getSeq());

    }

    @Test
    @Order(6)
    void testDelete(){
        Member member=new Member(1L,"test4","test4@mail.com","1234","testUser4","가입",LocalDateTime.now());
        memberRepository.save(member);

        List<Member> members=memberService.getMembers();
        int beforeCount=members.size();

        memberService.deleteMember(member.getSeq());

        List<Member> afterMember=memberService.getMembers();
        int afterCount=afterMember.size();

        Assertions.assertThat(afterCount).isEqualTo(beforeCount-1);

    }

}