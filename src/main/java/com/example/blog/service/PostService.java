package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;

import java.util.List;

public interface PostService {

    void post(Post post, Member member);

    List<Post> getPostList(Member member);
}
