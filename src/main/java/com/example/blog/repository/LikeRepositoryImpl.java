package com.example.blog.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LikeRepositoryImpl implements LikesRepository {

    private JdbcTemplate templates;

    public LikeRepositoryImpl(DataSource dataSource) {
        this.templates = new JdbcTemplate(dataSource);
    }

    @Override
    public Integer isLike(Long postSeq, Long memberSeq) {
        String sql = "select count(*) from likes where post_seq = ? and member_seq = ?";
        return templates.queryForObject(sql, Integer.class, postSeq, memberSeq);
    }

    @Override
    public void addLike(Long postSeq, Long memberSeq) {
        long likeSeq = getLastLikesSeq() + 1;
        String sql = "insert into likes values (?, ?, ?)";
        templates.update(sql, likeSeq, memberSeq, postSeq);
    }

    @Override
    public void removeLike(Long postSeq, Long memberSeq) {
        String sql = "delete from likes where post_seq = ? and member_seq = ?";
        templates.update(sql, postSeq, memberSeq);

    }

    @Override
    public Integer countByPostSeq(Long postSeq) {
        String sql = "select count(*) from likes where post_seq = ?";
        return templates.queryForObject(sql, Integer.class, postSeq);
    }

    private Long getLastLikesSeq() {
        Integer countLike = templates.queryForObject("select count(*) from likes", Integer.class);
        if (countLike == 0) {
            return 0L;
        }
        String sql = "select like_seq from likes order by like_seq desc limit 1";
        return templates.queryForObject(sql, Long.class);
    }
}
