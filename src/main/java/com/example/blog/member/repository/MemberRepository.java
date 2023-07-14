package com.example.blog.member.repository;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;

import java.util.List;

public interface MemberRepository {

    void save(Member member);

    Integer countByEmail(String email);

    Integer countByName(String name);

    Member findByEmail(String email);
}
