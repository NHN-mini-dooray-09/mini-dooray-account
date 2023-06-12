package com.nhnacademy.account;


import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private MemberRepository memberRepository;

    public DataLoader(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        Member admin= new Member("admin","admin","admin@mail.com","admin1","가입","ROLE_ADMIN");
        memberRepository.save(admin);
    }
}
