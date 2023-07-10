package com.example.blog.repository;

import com.example.blog.member.domain.Member;
import com.example.blog.member.repository.MemberRepository;
import com.example.blog.member.repository.MemberRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static com.example.blog.connection.ConnectionConst.*;

public class MemberRepositoryTest {

    private final DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
    private MemberRepository memberRepository = new MemberRepositoryImpl(dataSource);


    @Test
    void 저장() {
        Member member = new Member(1L, "id1", "pw1", "name1");
        Member saveMember = memberRepository.save(member);
    }

    @Test
    void 회원찾기() {
        Member member = new Member(2L, "id2", "pw2", "name2");
        memberRepository.save(member);

        Member findMember = memberRepository.findById("id2");

        Assertions.assertThat(findMember.getMemberName()).isEqualTo(member.getMemberName());
    }
}
