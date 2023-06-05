package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostService {

    void post(Post post, Member member);

    List<Post> getPostList();

    Post getPost(Long postSeq);

    void updatePost(PostForm postForm, Long postSeq);

    void deletePost(Long postSeq);
}
