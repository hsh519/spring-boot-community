package com.example.blog.repository;

import com.example.blog.domain.Member;

public interface MemberRepository {

    Member save(Member member);
    Member findById(String memberId);
}
