package com.example.blog.likes.repository;

public interface LikesRepository {
    Integer getLike(Long postSeq, Long memberSeq);

    Integer countLikes(Long postSeq);

    void addLike(Long postSeq, Long memberSeq);

    void removeLike(Long postSeq, Long memberSeq);
}
