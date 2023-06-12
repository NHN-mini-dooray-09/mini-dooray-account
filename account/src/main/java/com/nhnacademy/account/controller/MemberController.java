package com.nhnacademy.account.controller;



import com.nhnacademy.account.domain.request.CheckEmailDto;
import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;

import com.nhnacademy.account.domain.response.LoginDto;
import com.nhnacademy.account.domain.response.MemberSeqDto;

import com.nhnacademy.account.domain.response.UpdatedStatusDto;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.exception.EmailNotFoundException;
import com.nhnacademy.account.exception.LoginFailedException;
import com.nhnacademy.account.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class MemberController {

    private final MemberService memberService;



    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberSeqDto> createMember(@RequestBody CreateMemberDto createMemberDto){
        MemberSeqDto memberSeqDto=memberService.createMember(createMemberDto);
        return ResponseEntity.ok(memberSeqDto);
    }

    @GetMapping("/loginId/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginDto> resultLogin(@PathVariable String id){
        LoginDto member=memberService.resultLogin(id);
        return ResponseEntity.ok(member);
    }



    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody CheckIdAndPasswordDto dto) {
        try {
            memberService.login(dto);
            return ResponseEntity.ok("로그인 성공");
        }catch (LoginFailedException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/login/email")
    public ResponseEntity<String> emailLogin(@RequestBody CheckEmailDto dto){
      try {
          memberService.emailLogin(dto);
          return ResponseEntity.ok("로그인 성공");
      }catch (EmailNotFoundException e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
    }

    @PutMapping("/{seq}/drop")
    public ResponseEntity<UpdatedStatusDto> dropMember(@PathVariable Long seq){
        UpdatedStatusDto updatedStatusDto=memberService.dropMember(seq);
        return ResponseEntity.ok(updatedStatusDto);
    }

    @PutMapping("/{adminSeq}/sleep/{memberSeq}")
    public ResponseEntity<UpdatedStatusDto> sleepMember(@PathVariable Long adminSeq,@PathVariable Long memberSeq){
        UpdatedStatusDto updatedStatusDto=memberService.sleepMember(adminSeq,memberSeq);
        return ResponseEntity.ok(updatedStatusDto);
    }





//    @PostMapping("/accounts/drop/{seq}")
//    @ResponseStatus(HttpStatus.OK)
//    public Member dropMember(@PathVariable Long seq){
//        return memberService.dropMember(seq);
//    }
//
//    @PostMapping("account/sleep/{sep}")
//    @ResponseStatus(HttpStatus.OK)
//    public Member sleepMember(@PathVariable Long sep){
//        return memberService.sleepMember(sep);
//    }
//
//    @GetMapping("/accounts")
//    public List<Member> getMembers(){
//        return memberService.getMembers();
//    }
//
//    @GetMapping("/accounts/{seq}")
//    public Member getMember(@PathVariable Long seq){
//        return memberService.getMember(seq);
//    }
}
