package com.example.blog.repository;

import com.example.blog.domain.Comment;

import java.util.List;

public interface CommentRepository {

    void save(Comment comment);

    List<Comment> findAll(Long postSeq);

    Integer commentCount(Long postSeq);
}
