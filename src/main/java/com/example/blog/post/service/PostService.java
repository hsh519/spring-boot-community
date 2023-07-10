package com.example.blog.post.service;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;

import java.util.List;

public interface PostService {

    void post(Post post, Member member);

    List<Post> getPostListBySearchKeyword(Long startSeq, Long pageCnt, String searchKeyword);

    List<Post> getPostListByCategoryAndSearch(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword);

    Post getPost(Long postSeq, Boolean isPostViewPlus);

    void updatePost(Post post, Long postSeq);

    void deletePost(Long postSeq);

    List<Post> getSearchPost(Long memberSeq, String searchKeyword, Long startSeq, Long pageCnt);

    Integer getPostCntByCategoryAndSearch(Long categorySeq, String searchKeyword);

    Integer getPostCntBySearchKeyword(String searchKeyword);

    Integer getCountSearchPost(Long memberSeq, String searchKeyword);

}
