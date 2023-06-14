package com.nhnacademy.account.controller;




import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;

import com.nhnacademy.account.domain.response.*;

import com.nhnacademy.account.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/signup")//멤버 회원가입 Seq
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MemberSeqDto> createMember(@RequestBody CreateMemberDto createMemberDto){
        MemberSeqDto memberSeqDto=memberService.createMember(createMemberDto);
        return ResponseEntity.ok(memberSeqDto);
    }

    @GetMapping("/login/{id}")//아이디로 유저의 아이디 비밀번호 조회
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginDto> resultLogin(@PathVariable String id){
        LoginDto member=memberService.resultLogin(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/check/{email}") //이메일로 아이디,비밀번호,이름,이메일 조회
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmailCheckDto> emailCheck(@PathVariable String email){
        EmailCheckDto member=memberService.emailCheck(email);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")// 실제 로그인
    @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<LoginResultDto> login(@RequestBody CheckIdAndPasswordDto dto) {
            LoginResultDto member=memberService.login(dto);
            return ResponseEntity.ok(member);
    }


    @PutMapping("/{seq}/drop")//사용자 상태 탈퇴로 변경
    public ResponseEntity<UpdatedStatusDto> dropMember(@PathVariable Long seq){
        UpdatedStatusDto updatedStatusDto=memberService.dropMember(seq);
        return ResponseEntity.ok(updatedStatusDto);
    }

//    @PutMapping("/{adminSeq}/sleep/{memberSeq}")
//    public ResponseEntity<UpdatedStatusDto> sleepMember(@PathVariable Long adminSeq,@PathVariable Long memberSeq){
//        UpdatedStatusDto updatedStatusDto=memberService.sleepMember(adminSeq,memberSeq);
//        return ResponseEntity.ok(updatedStatusDto);
//    }

    @GetMapping("/members/{id}")//해당 아이디 유저가 본인 제외 전 멤버의 아이디,이름,상태 조회가능
    public ResponseEntity<List<GetMembersDto>> getMembers(@PathVariable String id){
        List<GetMembersDto> members=memberService.getMembers(id);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/checkId/{id}")//아이디 중복 확인
    public ResponseEntity<Boolean> checkIdDuplicate(@PathVariable String id){
        boolean isDuplicate=memberService.checkIdDuplicate(id);
        return ResponseEntity.ok(isDuplicate);
    }
    @GetMapping("/checkEmail/{email}")//이메일 중복 확인
    public ResponseEntity<Boolean> checkEmailDuplicate(@PathVariable String email){
        boolean isDuplicate=memberService.checkEmailDuplicate(email);
        return ResponseEntity.ok(isDuplicate);
    }
}