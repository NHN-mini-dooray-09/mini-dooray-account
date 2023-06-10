package com.nhnacademy.account.service;

import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultMemberService implements MemberService{

    private final MemberRepository memberRepository;

    public DefaultMemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    @Override
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public boolean checkExist(String id, String password, LocalDateTime time) {
        Member member=memberRepository.findByIdAndPassword(id,password);
        return member != null;
    }

    @Override
    public boolean checkEmail(String email) {
        Member member=memberRepository.findByEmail(email);
        return member!=null;
    }

    @Override
    public Member dropMember(Long seq) {
        Member member=memberRepository.findById(seq).orElseThrow();
        member.setStatusName("탈퇴");
        return memberRepository.save(member);
    }

    @Override
    public Member sleepMember(Long seq) {
        Member member=memberRepository.findById(seq).orElseThrow();
        member.setStatusName("휴면");
        return memberRepository.save(member);
    }

    @Override
    public Member getMember(Long seq) {
        return memberRepository.findById(seq).orElseThrow();
    }

    @Override
    public void deleteMember(Long seq) {
        memberRepository.deleteById(seq);
    }
}
