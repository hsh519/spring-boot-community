package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> postAll(Long startSeq, Long pageCnt);

    void postViewPlus(Long postSeq);

    List<Post> findByCategory(Long categorySeq, Long startSeq, Long pageCnt);

    Post findBySeq(Long postSeq);

    void update(Post post, Long postSeq);

    void delete(Long postSeq);

    Integer postCnt();

    List<Post> findBySearch(String searchKeyword, Long startSeq, Long pageCnt);

    Integer postCntByCategory(Long categorySeq);

    Integer postCntBySearchKeyword(String searchKeyword);

}
