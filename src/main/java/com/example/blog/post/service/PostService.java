package com.example.blog.post.service;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;

import java.util.List;

public interface PostService {

    void post(Post post, Member member);

    Integer getPostsCnt(Long categorySeq, String searchKeyword);

    List<Post> getPosts(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword);

    Post getPost(Long postSeq);

    void updatePost(Post post, Long postSeq);

    void deletePost(Long postSeq);

    Integer getMyPostsCnt(Long memberSeq, String searchKeyword);

    List<Post> getMyPosts(Long memberSeq, String searchKeyword, Long startSeq, Long pageCnt);

}
