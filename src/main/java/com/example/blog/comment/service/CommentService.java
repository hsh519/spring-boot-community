package com.example.blog.comment.service;

import com.example.blog.comment.domain.Comment;
import com.example.blog.member.domain.Member;

import java.util.List;

public interface CommentService {

    void comment(Comment comment, Member member);

    List<Comment> getComments(Long postSeq);
}
