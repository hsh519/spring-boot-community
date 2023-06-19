package com.example.blog.service;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;

import java.util.List;

public interface CommentService {

    void comment(Comment comment, Member member);

    List<Comment> getCommentList(Long postSeq);

    Integer getCommentCnt(Long postSeq);
}
