package com.nhnacademy.account;


import com.nhnacademy.account.entity.Member;
import com.nhnacademy.account.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder;



    public DataLoader(MemberRepository memberRepository,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.memberRepository=memberRepository;
        this.passwordEncoder=bCryptPasswordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        String encodedPassword=passwordEncoder.encode("admin");
        Member admin= new Member("admin",encodedPassword,"admin@mail.com","admin1","가입","ROLE_ADMIN");
        memberRepository.save(admin);
    }
}
