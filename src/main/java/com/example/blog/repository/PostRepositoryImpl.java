package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;
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
public class PostRepositoryImpl implements PostRepository {

    private JdbcTemplate templates;

    public PostRepositoryImpl(DataSource dataSource) {
        this.templates = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Post post) {
        Long postSeq = getLastPostSeq() + 1;
        String sql = "insert into post values(?,?,?,?,?,?,?,?,?,?)";
        templates.update(
                sql, postSeq, post.getMemberSeq(), post.getPostName(),
                post.getPostWriter(), post.getPostContent(), post.getPostRegister(),
                post.getPostUpdate(), 0, 0, post.getCategorySeq()
        );
    }

    @Override
    public List<Post> postAll() {
        String sql = "select post_seq, post_name, post_writer, post_update from post";
        return templates.query(sql, postAllRowMapper());
    }

    @Override
    public Post findBySeq(Long postSeq) {
        String sql = "select * from post where post_seq = ?";
        return templates.queryForObject(sql, postRowMapper(), postSeq);
    }

    @Override
    public List<Post> findByCategory(Long categorySeq) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where category_seq = ?";
        return templates.query(sql, postAllRowMapper(), categorySeq);
    }

    @Override
    public void update(PostForm postForm, Long postSeq) {
        String sql = "update post set post_name=?, post_content=?, post_update=? where post_seq= ?";
        templates.update(sql, postForm.getPostName(), postForm.getPostContent(), getDateToString(), postSeq);
    }

    @Override
    public void delete(Long postSeq) {
        String sql = "delete from post where post_seq = ?";
        templates.update(sql, postSeq);
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
    private String getDateToString() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    private Long getLastPostSeq() {
        Integer countPost = templates.queryForObject("select count(*) from post", Integer.class);
        if (countPost == 0) {
            return 0L;
        }
        String sql = "select * from post order by post_seq desc limit 1";
        Post post = templates.queryForObject(sql, postRowMapper());
        return post.getPostSeq();
    }
}
