package com.example.blog.repository;

import com.example.blog.domain.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private JdbcTemplate templates;

    public PostRepositoryImpl(DataSource dataSource) {
        this.templates = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Post post) {
        String sql = "insert into post values(?,?,?,?,?,?,?,?)";
        templates.update(
                sql, post.getPostSeq(), post.getMemberSeq(), post.getPostName(),
                post.getPostWriter(), post.getPostContent(), post.getPostRegister(),
                post.getPostUpdate(), post.getPostTag()
        );
    }

    @Override
    public List<Post> postAll(Long memberSeq) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where member_seq = ?";
        return templates.query(sql, postAllRowMapper(), memberSeq);
    }

    @Override
    public Post findBySeq(Long postSeq) {
        String sql = "select * from post where post_seq = ?";
        return templates.queryForObject(sql, postRowMapper(), postSeq);
    }

    @Override
    public void update(Post post, Long postSeq) {
        String sql = "update post set post_name=?, post_content=?, post_update=?, post_tag=? where post_seq= ?";
        templates.update(sql, post.getPostName(), post.getPostContent(), getDateToString() ,post.getPostTag(), postSeq);
    }

    private RowMapper<Post> postAllRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostSeq(rs.getLong(1));
            post.setPostName(rs.getString(2));
            post.setPostWriter(rs.getString(3));
            post.setPostUpdate(rs.getString(4));

            return post;
        };
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostSeq(rs.getLong(1));
            post.setMemberSeq(rs.getLong(2));
            post.setPostName(rs.getString(3));
            post.setPostWriter(rs.getString(4));
            post.setPostContent(rs.getString(5));
            post.setPostRegister(rs.getString(6));
            post.setPostUpdate(rs.getString(7));
            post.setPostTag(rs.getString(8));

            return post;
        };
    }
    private static String getDateToString() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
