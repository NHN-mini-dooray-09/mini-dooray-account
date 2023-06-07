package com.nhnacademy.account.service;

import com.nhnacademy.account.entity.Member;

import java.util.List;

public interface MemberService {

    List<Member> getMembers();

    Member createMember(Member member);

    Member getMember(Long seq);

    void deleteMember(Long seq);

    boolean checkExist(String id,String password);

    boolean checkEmail(String email);
}
