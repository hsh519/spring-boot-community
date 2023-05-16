package com.example.blog.repository;

import com.example.blog.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private JdbcTemplate template;

    public MemberRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member values(?,?,?,?)";
        template.update(sql, member.getMemberSeq(), member.getMemberId(), member.getMemberPw(), member.getMemberName());
        return member;
    }

    @Override
    public Member findById(String memberId) {
        String sql = "select * from member where member_id = ?";
        return template.queryForObject(sql, memberRowMapper(), memberId);
    }


    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberSeq(rs.getLong("member_seq"));
            member.setMemberId(rs.getString("member_id"));
            member.setMemberPw(rs.getString("member_pw"));
            member.setMemberName(rs.getString("member_name"));
            return member;
        };
    }
}
