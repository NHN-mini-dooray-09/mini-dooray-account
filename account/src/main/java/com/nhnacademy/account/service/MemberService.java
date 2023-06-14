package com.nhnacademy.account.service;


import com.nhnacademy.account.domain.request.CheckIdAndPasswordDto;
import com.nhnacademy.account.domain.request.CreateMemberDto;

import com.nhnacademy.account.domain.response.*;
import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.exception.AuthErrorException;
import com.nhnacademy.account.exception.FailedFindSeqException;
import com.nhnacademy.account.exception.LoginFailedException;
import com.nhnacademy.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional
    public MemberSeqDto createMember(CreateMemberDto createMemberDto) {
        String role=memberRepository.count()==0? "ROLE_ADMIN":"ROLE_USER";
        Member newMember=Member.builder()
                .id(createMemberDto.getId())
                .password(createMemberDto.getPassword())
                .email(createMemberDto.getEmail())
                .name(createMemberDto.getName())
                .status("가입")
                .time(LocalDate.now())
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
    public LoginResultDto login(CheckIdAndPasswordDto dto)  {
        Member member=memberRepository.findByIdAndPassword(dto.getId(), dto.getPassword());
        if (member == null){
            throw new LoginFailedException();
        }
        member.loginTime(LocalDate.now());
        memberRepository.save(member);
        return new LoginResultDto(member.getId(),LocalDate.now());
    }

        @Transactional
        public UpdatedStatusDto dropMember(Long memberSeq){
            Member member=memberRepository.findById(memberSeq)
                    .orElseThrow(()->new FailedFindSeqException("Member not found" + memberSeq));
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

    public List<GetMembersDto> getMembers(String id){
        Member thismember=memberRepository.findById(id);

        List<Member>members=memberRepository.findAllByIdNot(thismember.getId());
        List<GetMembersDto>getMembersDtoList=new ArrayList<>();

        for (Member member:members){
            if (!member.getRole().equals("ROLE_ADMIN")){
                GetMembersDto dto=new GetMembersDto(
                        member.getId(),
                        member.getName(),
                        member.getStatus()
                );
                getMembersDtoList.add(dto);
            }
        }
        return getMembersDtoList;
    }

    public boolean checkIdDuplicate(String id){
        return memberRepository.existsById(id);
    }

}
