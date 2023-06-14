package com.nhnacademy.account.controller;

import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;
import com.nhnacademy.account.domain.response.*;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.service.MemberService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import java.lang.Exception;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("dev")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;


    @Test
    @Order(1)
    void testCreateMember()throws Exception{
        ObjectMapper objectMapper=new ObjectMapper();
        CreateMemberDto createMemberDto= CreateMemberDto.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .status("가입")
                .role("ROLE_USER")
                .build();
        MemberSeqDto memberSeqDto=MemberSeqDto.builder()
                .memberSeq(1L)
                .build();
        Mockito.when(memberService.createMember(Mockito.any(CreateMemberDto.class)))
                .thenReturn(memberSeqDto);
        mockMvc.perform(post("/accounts/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberSeq").value(1L));
    }
    @Test
    @Order(2)
    void testResultLogin()throws Exception{
        Member member=Member.builder()
                .id("test")
                .password("1234")
                .build();
        LoginDto loginDto=new LoginDto(member.getId(),member.getPassword());
        Mockito.when(memberService.resultLogin(Mockito.anyString()))
                .thenReturn(loginDto);

        mockMvc.perform(get("/accounts/login/{id}",member.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()))
                .andExpect(jsonPath("$.password").value(member.getPassword()));
    }

    @Test
    @Order(3)
    void testEmailCheck()throws Exception{
        Member member=Member.builder()
                .id("test")
                .password("1234")
                .email("test@mail.com")
                .name("testUser")
                .build();
        EmailCheckDto dto=new EmailCheckDto(member.getId(),member.getPassword(),member.getEmail(),member.getName());
        when(memberService.emailCheck(anyString()))
                .thenReturn(dto);

        mockMvc.perform(get("/accounts/check/{email}",member.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(member.getId()))
                .andExpect(jsonPath("$.password").value(member.getPassword()))
                .andExpect(jsonPath("$.email").value(member.getEmail()))
                .andExpect(jsonPath("$.name").value(member.getName()));
    }

    @Test
    @Order(4)
    void testLogin()throws Exception{
        ObjectMapper mapper=new ObjectMapper();
        CheckIdAndPasswordDto checkIdAndPasswordDto= CheckIdAndPasswordDto.builder()
                .id("test")
                .password("1234")
                .build();
        LoginResultDto resultDto=new LoginResultDto(checkIdAndPasswordDto.getId(),LocalDate.now());
        Mockito.when(memberService.login(Mockito.any(CheckIdAndPasswordDto.class)))
                .thenReturn(resultDto);

        mockMvc.perform(post("/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(checkIdAndPasswordDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test"))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()));
    }
    @Test
    @Order(5)
    void testDropMember()throws Exception{
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                ,LocalDate.now());
        when(memberService.dropMember(any(Long.class)))
                .thenReturn(new UpdatedStatusDto("탈퇴한 유저입니다.","탈퇴"));

        mockMvc.perform(put("/accounts/{seq}/drop",member.getMemberSeq())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("탈퇴한 유저입니다."))
                .andExpect(jsonPath("$.status").value("탈퇴"));
    }

//    @Test
//    @Order(6)
//    void testSleepMember()throws Exception{
//        Member admin=new Member(
//                1L
//                ,"admin"
//                ,"1234"
//                ,"admin@mail.com"
//                ,"testAdmin"
//                ,"가입"
//                ,"ROLE_ADMIN"
//                ,LocalDate.now());
//        Member user=new Member(
//                2L
//                ,"test"
//                ,"1234"
//                ,"test@mail.com"
//                ,"testUser"
//                ,"가입"
//                ,"ROLE_USER"
//                ,LocalDate.now());
//
//        when(memberService.sleepMember(admin.getMemberSeq(),user.getMemberSeq()))
//                .thenReturn(new UpdatedStatusDto("휴면 유저입니다.","휴면"));
//
//        mockMvc.perform(put("/accounts/{adminSeq}/sleep/{memberSeq}",admin.getMemberSeq(),user.getMemberSeq())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("휴면 유저입니다."))
//                .andExpect(jsonPath("$.status").value("휴면"));
//    }

    @Test
    @Order(6)
    void testGetMemberS()throws Exception{
        Member thisMember = new Member(
                1L
                , "test"
                , "1234"
                , "test@mail.com"
                , "testUser"
                , "가입"
                , "ROLE_USER"
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
        when(memberService.getMembers(any(String.class))).thenReturn(
                members.stream()
                        .map(member -> new GetMembersDto(member.getId(), member.getName(), member.getStatus()))
                        .collect(Collectors.toList()));

        mockMvc.perform(get("/accounts/members/{id}",thisMember.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("user1"))
                .andExpect(jsonPath("$[0].name").value("사용자1"))
                .andExpect(jsonPath("[0].status").value("가입"))

                .andExpect(jsonPath("$[1].id").value("user2"))
                .andExpect(jsonPath("$[1].name").value("사용자2"))
                .andExpect(jsonPath("[1].status").value("가입"));
    }

    @Test
    @Order(7)
    void testCheckIdDuplicate()throws Exception{
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                ,LocalDate.now());
        when(memberService.checkIdDuplicate(member.getId())).thenReturn(true);

        mockMvc.perform(get("/accounts/checkId/{id}","test"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    @Order(8)
    void testCheckEmailDuplicate()throws Exception{
        Member member=new Member(
                1L
                ,"test"
                ,"1234"
                ,"test@mail.com"
                ,"testUser"
                ,"가입"
                ,"ROLE_USER"
                ,LocalDate.now());
        when(memberService.checkEmailDuplicate(member.getEmail())).thenReturn(true);

        mockMvc.perform(get("/accounts/checkEmail/{email}","test@mail.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }
}