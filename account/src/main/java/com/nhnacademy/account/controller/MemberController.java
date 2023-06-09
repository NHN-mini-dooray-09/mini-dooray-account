package com.nhnacademy.account.controller;


import com.nhnacademy.account.domain.LoginDto;
import com.nhnacademy.account.domain.MemberDto;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.repository.MemberRepository;
import com.nhnacademy.account.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/accounts/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Member createMember(@RequestBody Member member){
        return memberService.createMember(member);
    }

    @GetMapping("/accounts/login")
    public ResponseEntity<String> checkExist(@RequestBody LoginDto loginDto){
        boolean isMemberExists=memberService.checkExist(loginDto.getId(),loginDto.getPassword());
        if (isMemberExists) {
            return ResponseEntity.ok("로그인 되었습니다.");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accounts/login/email")
    public ResponseEntity<String> getEmail(@RequestBody LoginDto loginDto){
        boolean emailExists=memberService.checkEmail(loginDto.getEmail());
        if (emailExists){
            return ResponseEntity.ok("이메일이 존재합니다");
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accounts")
    public List<Member> getMembers(){
        return memberService.getMembers();
    }

    @GetMapping("/accounts/{seq}")
    public Member getMember(@PathVariable Long seq){
        return memberService.getMember(seq);
    }

    @DeleteMapping("/accounts/{seq}")
    public MemberDto deleteMember(@PathVariable Long seq){
        memberService.deleteMember(seq);
        MemberDto dto=new MemberDto();
        dto.setResult("ok");
        return  dto;
    }

}
