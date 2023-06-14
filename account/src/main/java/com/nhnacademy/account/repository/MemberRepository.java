package com.nhnacademy.account.repository;

import com.nhnacademy.account.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByIdAndPassword(String id,String password);

    Member findByEmail(String email);

    Member findById(String id);

    List<Member> findAllByIdNot(String id);

    boolean existsById(String id);

    boolean existsByEmail(String email);
}
