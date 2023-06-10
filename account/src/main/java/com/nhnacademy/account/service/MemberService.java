package com.nhnacademy.account.service;

import com.nhnacademy.account.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberService {

    List<Member> getMembers();

    Member createMember(Member member);

    Member getMember(Long seq);

    void deleteMember(Long seq);

    boolean checkExist(String id, String password, LocalDateTime time);

    boolean checkEmail(String email);

    Member dropMember(Long seq);

    Member sleepMember(Long seq);

}
