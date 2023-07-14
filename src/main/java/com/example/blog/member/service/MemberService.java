package com.example.blog.member.service;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;

import java.util.List;

public interface MemberService {
    void register(Member member);

    Member login(String email);

    Integer countEmail(String email);

    Integer countName(String name);
}
