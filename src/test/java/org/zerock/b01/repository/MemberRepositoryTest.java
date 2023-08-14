package org.zerock.b01.repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
@Slf4j
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private  MemberRepository memberRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email" + i + "@aaa.bbb")
                    .build();

            member.addRole(MemberRole.USER);

            if(i>=90){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }


    @Test
    public void testRead() {
        Optional<Member> result = memberRepository.getWithRoles("member100");
        Member member = result.orElseThrow();
        log.info("member...{}",member);
        log.info("member.getRoleSet().....{}",member.getRoleSet());

        member.getRoleSet().forEach(memberRole -> log.info("name....{}", memberRole.name()));
    }

    @Commit
    @Test
    public void testUpdatePassword(){
        String mid = "kke2005@naver.com";
        String mpw = passwordEncoder.encode("54321");

        memberRepository.updatePassword(mpw, mid);
    }
}