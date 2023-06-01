package com.example.blog.repository;

import com.example.blog.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@Slf4j
public class MemberRepositoryImpl implements MemberRepository {

    private JdbcTemplate template;

    public MemberRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        Long memberId = findLastMemberSeq()+1;
        String sql = "insert into member values(?,?,?,?)";
        template.update(sql, memberId, member.getMemberId(), member.getMemberPw(), member.getMemberName());
        return member;
    }

    @Override
    public Member findById(String memberId) {
        try {
            String sql = "select * from member where member_id = ?";
            return template.queryForObject(sql, memberRowMapper(), memberId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Member findByName(String memberName) {
        try {
            String sql = "select * from member where member_name = ?";
            return template.queryForObject(sql, memberRowMapper(), memberName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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

    private Long findLastMemberSeq() {
        String sql = "select * from member order by member_seq desc limit 1";
        Member member = template.queryForObject(sql, memberRowMapper());
        return member.getMemberSeq();
    }
}
