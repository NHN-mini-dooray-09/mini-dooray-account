package com.nhnacademy.account.service;


import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;
import com.nhnacademy.account.domain.response.*;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.exception.LoginFailedException;
import com.nhnacademy.account.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;


    @Test
    @Order(1)
    void createMember(){
        CreateMemberDto createMemberDto=CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .build();
        Member member=new Member(
                1L
                ,createMemberDto.getId()
                ,createMemberDto.getPassword()
                ,createMemberDto.getEmail()
                ,createMemberDto.getName()
                ,createMemberDto.getStatus()
                ,createMemberDto.getRole()
                ,createMemberDto.getTime());
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        MemberSeqDto result = memberService.createMember(createMemberDto);

        assertNotNull(result);
        assertEquals(1L, result.getMemberSeq());
    }



    @Test
    @Order(2)
    void resultLogin(){
        String memberId="test";
        Member member=Member.builder()
                .id(memberId)
                .password("1234")
                .build();
        when(memberRepository.findById(memberId)).thenReturn(member);

        LoginDto loginDto=memberService.resultLogin(memberId);
        Assertions.assertEquals(memberId,loginDto.getId());
        Assertions.assertEquals(member.getPassword(),loginDto.getPassword());
    }
    @Test
    @Order(3)
    void emailCheck(){
        String email="test@mail.com";
        Member member=Member.builder()
                .id("test")
                .password("password")
                .email(email)
                .name("testUser")
                .build();
        when(memberRepository.findByEmail(email)).thenReturn(member);
        EmailCheckDto dto=memberService.emailCheck(email);
        Assertions.assertEquals(member.getId(),dto.getId());
        Assertions.assertEquals(member.getPassword(),dto.getPassword());
        Assertions.assertEquals(member.getEmail(),dto.getEmail());
        Assertions.assertEquals(member.getName(),dto.getName());
    }

    @Test
    @Order(4)
    void testLogin(){
        CheckIdAndPasswordDto dto= CheckIdAndPasswordDto.builder()
                .id("test")
                .password("1234")
                .build();
        Member member=Member.builder()
                .id("test")
                .password("1234")
                .build();
        when(memberRepository.findByIdAndPassword(dto.getId(),dto.getPassword())).thenReturn(member);

        LoginResultDto resultDto=memberService.login(dto);

        Assertions.assertEquals(member.getId(),resultDto.getId());
        Assertions.assertEquals(LocalDate.now(),resultDto.getDate());
        verify(memberRepository,atLeastOnce()).save(member);
    }

    @Test
    @Order(5)
    void testDropMember(){
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                ,LocalDate.now());
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        UpdatedStatusDto result=memberService.dropMember(1L);
        assertNotNull(result);
        assertEquals("탈퇴한 유저입니다.",member.getName());
        assertEquals("탈퇴",member.getStatus());
    }

//    @Test
//    @Order(6)
//    void testSleepMember(){
//        Long adminSeq=1L;
//        Long memberSeq=2L;
//        Member testAdmin=new Member(
//                adminSeq
//                ,"admin"
//                ,"1234"
//                ,"admin@mail.com"
//                ,"testAdmin"
//                ,"가입"
//                ,"ROLE_ADMIN"
//                ,LocalDate.now());
//        Member testUser=new Member(
//                memberSeq
//                ,"user"
//                ,"1234"
//                ,"user@mail.com"
//                ,"testUser"
//                ,"가입"
//                ,"ROLE_USER"
//                ,LocalDate.now());
//        when(memberRepository.findById(adminSeq)).thenReturn(Optional.of(testAdmin));
//        when(memberRepository.findById(memberSeq)).thenReturn(Optional.of(testUser));
//
//        UpdatedStatusDto result=memberService.sleepMember(adminSeq,memberSeq);
//
//
//        assertEquals("휴면 유저입니다.",result.getName());
//        assertEquals("휴면",result.getStatus());
//    }

    @Test
    @Order(6)
    void testGetMembers(){
        Member thisMember = new Member(
                1L
                , "test"
                , "1234"
                , "test@mail.com"
                , "testUser"
                , "가입"
                , "ROLE_ADMIN"
                , LocalDate.now());
        List<Member> members = new ArrayList<>();
        members.add(new Member(
                2L
                , "user1"
                , "pass1"
                , "user1@mail.com"
                , "사용자1"
                , "가입"
                , "ROLE_ADMIN"
                , LocalDate.now()));
        members.add(new Member(
                3L
                , "user2"
                , "pass2"
                , "user2@mail.com"
                , "사용자2"
                , "가입"
                , "ROLE_USER"
                , LocalDate.now()));
        when(memberRepository.findById("test")).thenReturn(thisMember);
        when(memberRepository.findAllByIdNot("test")).thenReturn(members);

        List<GetMembersDto> result=memberService.getMembers("test");

        assertNotNull(result);
        assertEquals(2,result.size());

        GetMembersDto firstDto=result.get(0);
        assertEquals("user1",firstDto.getId());
        assertEquals("사용자1",firstDto.getName());
        assertEquals("가입",firstDto.getStatus());
    }

    @Test
    @Order(7)
    void testCheckIdDuplicate(){
        String id="test";
        when(memberRepository.existsById(id)).thenReturn(true);

        boolean result=memberService.checkIdDuplicate(id);
        assertTrue(result);
        verify(memberRepository).existsById(id);
    }

    @Test
    @Order(8)
    void testCheckEmailDuplicate(){
        String email="test@mail.com";
        when(memberRepository.existsByEmail(email)).thenReturn(true);

        boolean result=memberService.checkEmailDuplicate(email);
        assertTrue(result);
        verify(memberRepository).existsByEmail(email);
    }
}