package com.example.blog.repository;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;

import java.util.List;

public interface MemberRepository {

    Member save(Member member);
    Member findById(String memberId);
    Member findByName(String memberName);
    List<Post> getMyPost(Long memberId);
}
