package com.nhnacademy.account.service;

import com.nhnacademy.account.domain.request.CheckEmailDto;
import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;

import com.nhnacademy.account.domain.response.EmailCheckDto;
import com.nhnacademy.account.domain.response.LoginDto;
import com.nhnacademy.account.domain.response.MemberSeqDto;
import com.nhnacademy.account.domain.response.UpdatedStatusDto;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.exception.AuthErrorException;
import com.nhnacademy.account.exception.EmailNotFoundException;
import com.nhnacademy.account.exception.FailedFindSeqException;
import com.nhnacademy.account.exception.LoginFailedException;
import com.nhnacademy.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public MemberSeqDto createMember(CreateMemberDto createMemberDto) {
        String role= createMemberDto.getId().contains("admin")?"ROLE_ADMIN":"ROLE_USER";
        Member newMember=Member.builder()
                .id(createMemberDto.getId())
                .password(createMemberDto.getPassword())
                .email(createMemberDto.getEmail())
                .name(createMemberDto.getName())
                .status("가입")
                .time(createMemberDto.getTime())
                .role(role)
                .build();

        Member member=memberRepository.save(newMember);
        return new MemberSeqDto(member.getMemberSeq());
    }


    public LoginDto resultLogin(String id){
        Member member=memberRepository.findById(id);
        if (member!=null){
            return new LoginDto(member.getId(),member.getPassword());
        }
        return new LoginDto();
    }

    public EmailCheckDto emailCheck(String email) {
        Member member=memberRepository.findByEmail(email);
        if (member!=null){
            return new EmailCheckDto(member.getId(),member.getPassword(),member.getEmail(),member.getName());
        }
        return new EmailCheckDto();
    }


    @Transactional
    public boolean login(CheckIdAndPasswordDto dto)  {
        Member member=memberRepository.findByIdAndPassword(dto.getId(), dto.getPassword());
        if (member == null){
            throw new LoginFailedException("400 error: ID나 패스워드가 일치하지 않습니다.");
        }
        return true;
    }



    @Transactional
    public UpdatedStatusDto dropMember(Long memberSeq){
        Member member=memberRepository.findById(memberSeq)
                .orElseThrow(()->new FailedFindSeqException("Member not found"+memberSeq));
        member.updateMember("탈퇴한 유저입니다.","탈퇴");

        return new UpdatedStatusDto(member.getName(),member.getStatus());
    }

    @Transactional
    public UpdatedStatusDto sleepMember(Long adminSeq,Long memberSeq){
        Member admin=memberRepository.findById(adminSeq)
                .orElseThrow(()->new FailedFindSeqException("Admin not found:" + adminSeq));
        if (!admin.getRole().equals("ROLE_ADMIN")){
            throw new AuthErrorException("관리자만 휴면 상태로 변경 가능합니다.");
        }
        Member member=memberRepository.findById(memberSeq)
                .orElseThrow(()->new FailedFindSeqException("Member not found:" + memberSeq));
        member.updateMember("휴면 유저입니다.","휴면");
        return new UpdatedStatusDto(member.getName(),member.getStatus());
    }



//    @Override
//    public Member dropMember(Long seq) {
//        Member member=memberRepository.findById(seq).orElseThrow();
//        member.setStatusName("탈퇴");
//        return memberRepository.save(member);
//    }
//
//    @Override
//    public Member sleepMember(Long seq) {
//        Member member=memberRepository.findById(seq).orElseThrow();
//        member.setStatusName("휴면");
//        return memberRepository.save(member);
//    }
//
//    @Override
//    public Member getMember(Long seq) {
//        return memberRepository.findById(seq).orElseThrow();
//    }

}
