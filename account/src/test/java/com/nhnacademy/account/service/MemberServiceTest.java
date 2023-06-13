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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.ExpectedCount.times;


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
        CreateMemberDto createMemberDto = CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .role("ROLE_USER")
                .build();

        // When
        when(memberRepository.count()).thenReturn(0L);
        when(memberRepository.save(any(Member.class))).thenReturn(
                Member.builder()
                        .id(createMemberDto.getId())
                        .password(createMemberDto.getPassword())
                        .email(createMemberDto.getEmail())
                        .name(createMemberDto.getName())
                        .status(createMemberDto.getStatus())
                        .role(createMemberDto.getRole())
                        .build()
        );

        MemberSeqDto seqDto=memberService.createMember(createMemberDto);
        verify(memberRepository, atLeastOnce()).count();
        verify(memberRepository,atLeastOnce()).save(any(Member.class));
        Assertions.assertEquals(1L,seqDto.getMemberSeq());


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
    void testLoginFailedException() {
        CheckIdAndPasswordDto dto = CheckIdAndPasswordDto.builder()
                .id("test")
                .password("wrongpassword")
                .build();
        when(memberRepository.findByIdAndPassword(dto.getId(), dto.getPassword())).thenReturn(null);

        assertThrows(LoginFailedException.class, () -> memberService.login(dto));
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    @Order(6)
    void testDropMember(){
        Long memberSeq = 1L;
        Member member = Member.builder()
                .name("John")
                .status("가입")
                .build();
        when(memberRepository.findById(memberSeq)).thenReturn(Optional.of(member));

        // When
        UpdatedStatusDto resultDto = memberService.dropMember(memberSeq);

        // Then
        assertEquals("탈퇴한 유저입니다.", resultDto.getName());
        assertEquals("탈퇴", resultDto.getStatus());
        verify(memberRepository, atLeastOnce()).findById(memberSeq);
        verify(memberRepository, atLeastOnce()).save(member);
    }
}