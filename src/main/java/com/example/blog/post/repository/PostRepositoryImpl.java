package com.example.blog.post.repository;

import com.example.blog.post.domain.Post;
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
        String sql = "insert into post values(?,?,?,?,?,?,?,?,?,?,?)";
        templates.update(
                sql, postSeq, post.getMemberSeq(), post.getPostName(),
                post.getPostWriter(), post.getPostContent(), post.getPostRegister(),
                post.getPostUpdate(), 0, 0, post.getCategorySeq(), 0
        );
    }

    @Override
    public List<Post> postListBySearchKeyword(Long startSeq, Long pageCnt, String searchKeyword) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where post_name like ? order by post_seq desc limit ? offset ?";
        return templates.query(sql, postAllRowMapper(), "%" + searchKeyword + "%", pageCnt, startSeq);
    }

    @Override
    public void postViewPlus(Long postSeq) {
        String sql = "update post set post_view = post_view + 1 where post_seq = ?";
        templates.update(sql, postSeq);
    }

    @Override
    public List<Post> findByCategoryAndSearch(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where category_seq = ? and post_name like ? order by post_seq desc limit ? offset ?";
        return templates.query(sql, postAllRowMapper(), categorySeq, "%" + searchKeyword + "%", pageCnt, startSeq);
    }

    @Override
    public Post findBySeq(Long postSeq) {
        String sql = "select * from post where post_seq = ?";
        return templates.queryForObject(sql, postRowMapper(), postSeq);
    }

    @Override
    public void update(Post post, Long postSeq) {
        String sql = "update post set post_name=?, post_content=?, post_update=?, category_seq=? where post_seq= ?";
        templates.update(sql, post.getPostName(), post.getPostContent(), getDateToString(), post.getCategorySeq(), postSeq);
    }

    @Override
    public void delete(Long postSeq) {
        String sql = "delete from post where post_seq = ?";
        templates.update(sql, postSeq);
    }

    @Override
    public Integer postCnt() {
        String sql = "select count(*) from post";
        return templates.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Post> findBySearch(String searchKeyword, Long startSeq, Long pageCnt) {
        String sql = "select post_seq, post_name, post_writer, post_update from post where post_name like ? order by post_seq limit ? offset ?";
        return templates.query(sql, postAllRowMapper(), "%"+searchKeyword+"%", pageCnt, startSeq);
    }

    @Override
    public Integer postCntByCategoryAndSearch(Long categorySeq, String searchKeyword) {
        String sql = "select count(*) from post where category_seq = ? and post_name like ?";
        return templates.queryForObject(sql, Integer.class, categorySeq, "%" + searchKeyword + "%");
    }

    @Override
    public Integer postCntBySearchKeyword(String searchKeyword) {
        String sql = "select count(*) from post where post_name like ?";
        return templates.queryForObject(sql, Integer.class,"%" + searchKeyword + "%");
    }

    @Override
    public List<Post> findByMemberIdAndKeyword(Long memberSeq, String searchKeyword, Long startSeq, Long pageCnt) {
        String sql = "select post_seq, post_name, post_update from post where post_name like ? and member_seq = ? order by post_seq limit ? offset ?";
        return templates.query(sql, myPostsRowMapper(), "%" + searchKeyword + "%", memberSeq, pageCnt, startSeq);
    }

    @Override
    public Integer countPostBySeqAndKeyword(Long memberSeq, String searchKeyword) {
        String sql = "select count(*) from post where post_name like ? and member_seq = ?";
        return templates.queryForObject(sql, Integer.class, "%" + searchKeyword + "%", memberSeq);
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

    private RowMapper<Post> myPostsRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setPostSeq(rs.getLong(1));
            post.setPostName(rs.getString(2));
            post.setPostUpdate(rs.getString(3));

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
                post.setPostView(rs.getInt(8));
                post.setPostLike(rs.getInt(9));
                post.setCategorySeq(rs.getLong(10));

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
        String sql = "select post_seq from post order by post_seq desc limit 1";
        return templates.queryForObject(sql, Long.class);
    }
}
