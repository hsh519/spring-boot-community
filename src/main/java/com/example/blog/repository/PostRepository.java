package com.example.blog.repository;

import com.example.blog.domain.Post;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> postAll();

    Post findBySeq(Long postSeq);

    void update(Post post, Long postSeq);

    void delete(Long postSeq);
}
