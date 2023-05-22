package com.example.blog.repository;

import com.example.blog.domain.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
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
}
