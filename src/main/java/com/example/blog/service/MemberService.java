package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {
    void register(Member member);
    Member login(Member member);

    List<Post> myPost(Member member, Long startSeq, Long pageCnt);

    Integer getMyPostCnt(Long postId);
}
