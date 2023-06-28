package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostService {

    void post(Post post, Member member);

    List<Post> getPostListBySearchKeyword(Long startSeq, Long pageCnt, String searchKeyword);

    List<Post> getPostListByCategoryAndSearch(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword);

    Post getPost(Long postSeq, Boolean isPostViewPlus);

    void updatePost(Post post, Long postSeq);

    void deletePost(Long postSeq);

    Integer getPostCnt();

    List<Post> search(String searchKeyword, Long startSeq, Long pageCnt);

    Integer getPostCntByCategoryAndSearch(Long categorySeq, String searchKeyword);

    Integer getPostCntBySearchKeyword(String searchKeyword);

}
