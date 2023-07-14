package com.example.blog.comment.repository;

import com.example.blog.comment.domain.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {

    private JdbcTemplate templates;

    public CommentRepositoryImpl(DataSource dataSource) {
        this.templates = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Comment comment) {
        String sql = "insert into comment values(?,?,?,?,?,?)";
        templates.update(
                sql, getLastCommentSeq()+1, comment.getPostSeq(), comment.getMemberSeq(),
                comment.getCommentContent(), comment.getCommentWriter(),
                comment.getCommentUpdate());
    }

    @Override
    public List<Comment> findAll(Long postSeq) {
        String sql = "select comment_content, comment_writer, comment_update from comment where post_seq = ?";
        return templates.query(sql, commentsRowMapper(), postSeq);
    }

    @Override
    public Integer commentCount(Long postSeq) {
        String sql = "select count(*) from comment where post_seq = ?";
        return templates.queryForObject(sql, Integer.class, postSeq);
    }

    private RowMapper<Comment> commentsRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setCommentContent(rs.getString(1));
            comment.setCommentWriter(rs.getString(2));
            comment.setCommentUpdate(rs.getString(3));
            return comment;
        };
    }

    private Long getLastCommentSeq() {
        try {
            String sql = "select comment_seq from comment order by comment_seq desc limit 1";
            return templates.queryForObject(sql, Long.class);
        } catch (RuntimeException e) {
            return 0L;
        }
    }
}
