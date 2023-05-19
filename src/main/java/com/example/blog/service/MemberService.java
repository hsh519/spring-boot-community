package com.example.blog.service;

import com.example.blog.domain.Member;

import java.sql.SQLException;

public interface MemberService {
    void register(Member member);
    Member login(Member member);
}
