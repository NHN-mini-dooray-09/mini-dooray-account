package com.nhnacademy.account.repository;

import com.nhnacademy.account.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByIdAndPassword() {
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                , LocalDate.now());
        memberRepository.save(member);
        Member findMember=memberRepository.findByIdAndPassword(member.getId(),member.getPassword());

        Assertions.assertNotNull(findMember);
        Assertions.assertEquals("test",findMember.getId());
        Assertions.assertEquals("1234",findMember.getPassword());
    }

    @Test
    void findByEmail() {
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                , LocalDate.now());
        memberRepository.save(member);
        Member findMember=memberRepository.findByEmail(member.getEmail());
        Assertions.assertNotNull(findMember);
        Assertions.assertEquals("test@mail.com",findMember.getEmail());

    }

    @Test
    void findById() {
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                , LocalDate.now());
        memberRepository.save(member);
        Member findMember=memberRepository.findById(member.getId());
        Assertions.assertNotNull(findMember);
        Assertions.assertEquals("test",findMember.getId());
    }

    @Test
    void findAllByNot(){
        Member member1 = new Member(
                1L,
                "test1",
                "1234",
                "test1@mail.com",
                "testUser1",
                "가입",
                "ROLE_USER",
                LocalDate.now());
        memberRepository.save(member1);

        Member member2 = new Member(
                2L,
                "test2",
                "5678",
                "test2@mail.com",
                "testUser2",
                "가입",
                "ROLE_USER",
                LocalDate.now());
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAllByIdNot(member1.getId());

        Assertions.assertEquals(1, members.size());
        Assertions.assertEquals("test2", members.get(0).getId());
    }
}