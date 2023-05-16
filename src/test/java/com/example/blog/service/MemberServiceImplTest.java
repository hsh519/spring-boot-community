package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.repository.MemberRepository;
import com.example.blog.repository.MemberRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

import java.sql.SQLException;

import static com.example.blog.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberServiceImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @TestConfiguration
    static class config {

        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }

        @Bean
        TransactionManager txManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        MemberRepository memberRepository() { return new MemberRepositoryImpl(dataSource()); }

        @Bean
        MemberService memberService() { return new MemberServiceImpl(memberRepository()); }
    }


    @Test
    void 회원가입() throws SQLException {
        Member member = new Member(3L, "id3", "pw3", "name3");
        memberService.register(member);
        Member findMember = memberRepository.findById(member.getMemberId());
        assertThat(findMember.getMemberName()).isEqualTo(member.getMemberName());
    }
}
