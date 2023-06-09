package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> postAll();

    Post findBySeq(Long postSeq);

    List<Post> findByCategory(Long categorySeq);

    void update(PostForm postForm, Long postSeq);

    void delete(Long postSeq);
}
