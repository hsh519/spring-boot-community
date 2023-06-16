package com.example.blog.repository;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        comment.setCommentSeq(getLastPCommentSeq()+1);
        log.info("comment = {}", comment.toString());

        String sql = "insert into comment values(?,?,?,?,?,?,?)";
        templates.update(
                sql, comment.getCommentSeq(), comment.getPostSeq(), comment.getMemberSeq(),
                comment.getComment_content(), comment.getComment_writer(),
                comment.getComment_register(), comment.getComment_update());
    }

    @Override
    public List<Comment> findAll(Long postSeq) {
        String sql = "select comment_content, comment_writer, comment_update from comment where post_seq = ?";
        return templates.query(sql, commentAllRowMapper(), postSeq);
    }

    private RowMapper<Comment> commentAllRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setComment_content(rs.getString(1));
            comment.setComment_writer(rs.getString(2));
            comment.setComment_update(rs.getString(3));
            return comment;
        };
    }

    private Long getLastPCommentSeq() {
        Integer countComment = templates.queryForObject("select count(*) from comment", Integer.class);
        if (countComment == 0) {
            return 0L;
        }
        String sql = "select comment_seq from comment order by comment_seq desc limit 1";
        return templates.queryForObject(sql, Long.class);
    }
}
