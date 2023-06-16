package com.example.blog.repository;

import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    List<Post> postAll();

    void postViewPlus(Long postSeq);

    List<Post> findByCategory(Long categorySeq);

    Post findBySeq(Long postSeq);

    void update(Post post, Long postSeq);

    void delete(Long postSeq);

}
