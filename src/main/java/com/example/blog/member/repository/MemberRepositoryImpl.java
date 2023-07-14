package com.example.blog.member.repository;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private JdbcTemplate template;

    public MemberRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Member member) {
        Long memberSeq = getLastMemberSeq()+1;
        String sql = "insert into member values(?,?,?,?)";
        template.update(sql, memberSeq, member.getMemberEmail(), member.getMemberPw(), member.getMemberName());
    }

    @Override
    public Integer countByEmail(String email) {
        try {
            String sql = "select count(*) from member where member_email = ?";
            return template.queryForObject(sql, Integer.class, email);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }

    }

    @Override
    public Integer countByName(String name) {
        try {
            String sql = "select * from member where member_name = ?";
            return template.queryForObject(sql, Integer.class, name);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "select * from member where member_email = ?";
        return template.queryForObject(sql, memberRowMapper(), email);
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
                Member member = new Member();
                member.setMemberSeq(rs.getLong("member_seq"));
                member.setMemberEmail(rs.getString("member_email"));
                member.setMemberPw(rs.getString("member_pw"));
                member.setMemberName(rs.getString("member_name"));
                return member;
        };
    }

    private Long getLastMemberSeq() {
        try {
            String sql = "select member_seq from member order by member_seq desc limit 1";
            return template.queryForObject(sql, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }

    }
}
