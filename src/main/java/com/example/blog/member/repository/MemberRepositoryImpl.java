package com.example.blog.member.repository;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
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
    public List<Post> getMyPost(Long memberId, Long startSeq, Long pageCnt) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where member_seq = ? order by post_seq desc limit ? offset ?";
        return template.query(sql, postAllMapper(), memberId, pageCnt, startSeq);
    }

    @Override
    public Integer myPostCnt(Long postId) {
        String sql = "select count(*) from post where member_seq = ?";
        return template.queryForObject(sql, Integer.class, postId);
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "select * from member where member_email = ?";
        return template.queryForObject(sql, memberRowMapper(), email);
    }

    private RowMapper<Post> postAllMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostSeq(rs.getLong(1));
            post.setPostName(rs.getString(2));
            post.setPostWriter(rs.getString(3));
            post.setPostUpdate(rs.getString(4));

            return post;
        };
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
        String sql = "select member_seq from member order by member_seq desc limit 1";
        return template.queryForObject(sql, Long.class);
    }
}
