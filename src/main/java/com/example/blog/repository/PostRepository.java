package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> postListBySearchKeyword(Long startSeq, Long pageCnt, String searchKeyword);

    void postViewPlus(Long postSeq);

    List<Post> findByCategoryAndSearch(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword);

    Post findBySeq(Long postSeq);

    void update(Post post, Long postSeq);

    void delete(Long postSeq);

    Integer postCnt();

    List<Post> findBySearch(String searchKeyword, Long startSeq, Long pageCnt);

    Integer postCntByCategoryAndSearch(Long categorySeq, String searchKeyword);

    Integer postCntBySearchKeyword(String searchKeyword);

}
