package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostService {

    void post(Post post, Member member);

    List<Post> getPostList();

    List<Post> getPostListInCategory(Long categorySeq);

    Post getPost(Long postSeq, Boolean isPostViewPlus);

    void updatePost(Post post, Long postSeq);

    void deletePost(Long postSeq);

}
