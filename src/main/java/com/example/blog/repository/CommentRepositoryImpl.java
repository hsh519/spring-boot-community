package com.example.blog.repository;

import com.example.blog.domain.Comment;
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

    private JdbcTemplate template;

    public CommentRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Comment comment) {
        comment.setCommentSeq(4L);
        String date = getDateToString();
        comment.setComment_register(date);
        comment.setComment_update(date);

        log.info("comment = {}", comment.toString());

        String sql = "insert into comment values(?,?,?,?,?,?,?)";
        template.update(
                sql, comment.getCommentSeq(), comment.getPostSeq(), comment.getMemberSeq(),
                comment.getComment_content(), comment.getComment_writer(),
                comment.getComment_register(), comment.getComment_update());
    }

    @Override
    public List<Comment> findAll(Long postSeq) {
        String sql = "select comment_content, comment_writer, comment_update from comment where post_seq = ?";
        return template.query(sql, commentAllRowMapper(), postSeq);
    }

    private static String getDateToString() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
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
}
