package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;

public interface PostService {

    void post(Post post, Member member);
}
