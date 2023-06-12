package com.nhnacademy.account.controller;




import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;

import com.nhnacademy.account.domain.response.*;

import com.nhnacademy.account.exception.LoginFailedException;
import com.nhnacademy.account.service.MemberService;
import lombok.RequiredArgsConstructor;

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

    @GetMapping("/login/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginDto> resultLogin(@PathVariable String id){
        LoginDto member=memberService.resultLogin(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/check/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmailCheckDto> emailCheck(@PathVariable String email){
        EmailCheckDto member=memberService.emailCheck(email);
        return ResponseEntity.ok(member);
    }



    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<LoginResultDto> login(@RequestBody CheckIdAndPasswordDto dto) {
            LoginResultDto member=memberService.login(dto);
            return ResponseEntity.ok(member);
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
