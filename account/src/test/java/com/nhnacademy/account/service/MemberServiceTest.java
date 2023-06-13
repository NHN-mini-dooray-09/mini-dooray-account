package com.nhnacademy.account.service;

import com.nhnacademy.account.DataLoader;
import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;
import com.nhnacademy.account.domain.response.*;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.exception.AuthErrorException;
import com.nhnacademy.account.exception.FailedFindSeqException;
import com.nhnacademy.account.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Order(1)
    @Transactional
    void testCreateMember(){
        CreateMemberDto createMemberDto=CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_USER")
                .build();
        MemberSeqDto seqDto=memberService.createMember(createMemberDto);

        Assertions.assertNotNull(seqDto);
        Assertions.assertNotNull(seqDto.getMemberSeq());

    }

    @Test
    @Order(2)
    @Transactional
    void testResultLogin(){
        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_USER")
                .build();


        memberService.createMember(createMemberDto);

        LoginDto loginDto = memberService.resultLogin("test");

        assertNotNull(loginDto);
        assertEquals("test", loginDto.getId());
        assertEquals("1234", loginDto.getPassword());
    }

    @Test
    @Order(3)
    @Transactional
    void emailCheck(){
        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_USER")
                .build();


        memberService.createMember(createMemberDto);

        EmailCheckDto emailCheckDto = memberService.emailCheck("test@mail.com");

        assertNotNull(emailCheckDto);
        assertEquals("test", emailCheckDto.getId());
        assertEquals("1234", emailCheckDto.getPassword());
        assertEquals("test@mail.com", emailCheckDto.getEmail());
        assertEquals("testUser", emailCheckDto.getName());
    }

    @Test
    @Order(4)
    @Transactional
    void login(){
        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_USER")
                .build();


        memberService.createMember(createMemberDto);

        CheckIdAndPasswordDto dto=CheckIdAndPasswordDto.builder()
                .id("test")
                .password("1234")
                .build();
        LoginResultDto resultDto=memberService.login(dto);

        assertNotNull(resultDto);
        assertNotNull("test",resultDto.getId());
        assertEquals(LocalDate.now(),resultDto.getDate());

        Member updateMember=memberRepository.findById("test");
        assertNotNull(updateMember);
        assertEquals(LocalDate.now(),updateMember.getTime());
    }

    @Test
    @Order(5)
    @Transactional
    void testDropMember(){
        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_USER")
                .build();
        MemberSeqDto memberSeqDto=memberService.createMember(createMemberDto);

        UpdatedStatusDto updatedStatusDto=memberService.dropMember(memberSeqDto.getMemberSeq());
        assertNotNull(updatedStatusDto);
        assertEquals("탈퇴한 유저입니다.",updatedStatusDto.getName());
        assertEquals("탈퇴",updatedStatusDto.getStatus());

        Member updateMember=memberRepository.findById(memberSeqDto.getMemberSeq()).orElse(null);
        assertNotNull(updateMember);
        assertEquals("탈퇴한 유저입니다.",updateMember.getName());
        assertEquals("탈퇴",updateMember.getStatus());
    }


    @Test
    @Order(6)
    @Transactional
    void testSleepMember(){
        CreateMemberDto createAdminDto = CreateMemberDto.builder()
                .id("testAdmin")
                .password("admin123")
                .email("admin@mail.com")
                .name("Admin User")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_ADMIN")
                .build();
        MemberSeqDto adminSeqDto = memberService.createMember(createAdminDto);

        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("Test User")
                .status("가입")
                .time(LocalDate.now())
                .role("ROLE_USER")
                .build();
        MemberSeqDto memberSeqDto = memberService.createMember(createMemberDto);

        assertThrows(AuthErrorException.class, () -> memberService.sleepMember(adminSeqDto.getMemberSeq(), memberSeqDto.getMemberSeq()));
    }
}