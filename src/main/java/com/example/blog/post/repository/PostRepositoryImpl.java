package com.example.blog.post.repository;

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
public class PostRepositoryImpl implements PostRepository {

    private JdbcTemplate templates;

    public PostRepositoryImpl(DataSource dataSource) {
        this.templates = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Post post) {
        String sql = "insert into post values(?,?,?,?,?,?,?,?)";
        templates.update(
                sql, getLastPostSeq() + 1, post.getMemberSeq(), post.getPostName(),
                post.getPostWriter(), post.getPostContent(),
                post.getPostUpdate(), 0, post.getCategorySeq()
        );
    }

    @Override
    public Integer countAllByKeyword(String searchKeyword) {
        String sql = "select count(*) from post where post_name like ?";
        return templates.queryForObject(sql, Integer.class,"%" + searchKeyword + "%");
    }

    @Override
    public List<Post> findAllByKeyword(Long startSeq, Long pageCnt, String searchKeyword) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where post_name like ? order by post_seq desc limit ? offset ?";
        return templates.query(sql, postsRowMapper(), "%" + searchKeyword + "%", pageCnt, startSeq);
    }

    @Override
    public Post findBySeq(Long postSeq) {
        String sql = "select post_seq, member_seq, post_name, post_content, category_seq from post where post_seq = ?";
        return templates.queryForObject(sql, postRowMapper(), postSeq);
    }

    @Override
    public void update(Post post, Long postSeq) {
        String sql = "update post set post_name=?, post_content=?, post_update=?, category_seq=? where post_seq= ?";
        templates.update(sql, post.getPostName(), post.getPostContent(), post.getPostUpdate(), post.getCategorySeq(), postSeq);
    }

    @Override
    public void delete(Long postSeq) {
        templates.update("delete from likes where post_seq=?", postSeq);
        templates.update("delete from comment where post_seq=?", postSeq);
        templates.update("delete from post where post_seq=?", postSeq);
    }

    @Override
    public Integer countMyPostsByKeyword(Long memberSeq, String searchKeyword) {
        String sql = "select count(*) from post where member_seq = ? and post_name like ?";
        return templates.queryForObject(sql, Integer.class, memberSeq, "%" + searchKeyword + "%");
    }

    @Override
    public List<Post> findMyPostsByKeyword(Long memberSeq, String searchKeyword, Long startSeq, Long pageCnt) {
        String sql = "select post_seq, post_name, post_update from post where member_seq = ? and post_name like ? order by post_seq desc limit ? offset ?";
        return templates.query(sql, myPostsRowMapper(), memberSeq, "%" + searchKeyword + "%", pageCnt, startSeq);
    }

    @Override
    public Integer countAllByCategoryAndKeyword(Long categorySeq, String searchKeyword) {
        String sql = "select count(*) from post where category_seq = ? and post_name like ?";
        return templates.queryForObject(sql, Integer.class, categorySeq, "%" + searchKeyword + "%");
    }

    @Override
    public List<Post> findAllByCategorySeqAndKeyword(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where category_seq = ? and post_name like ? order by post_seq desc limit ? offset ?";
        return templates.query(sql, postsRowMapper(), categorySeq, "%" + searchKeyword + "%", pageCnt, startSeq);
    }

    private RowMapper<Post> postsRowMapper() {
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
            post.setPostContent(rs.getString(4));
            post.setCategorySeq(rs.getLong(5));

            return post;
        };
    }

    private RowMapper<Post> myPostsRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostSeq(rs.getLong(1));
            post.setPostName(rs.getString(2));
            post.setPostUpdate(rs.getString(3));

            return post;
        };
    }

    private Long getLastPostSeq() {
        try {
            String sql = "select post_seq from post order by post_seq desc limit 1";
            return templates.queryForObject(sql, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return 0L;
        }
    }
}
