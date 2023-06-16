package com.example.blog.repository;

public interface LikesRepository {
    Integer isLike(Long postSeq, Long memberSeq);

    void addLike(Long postSeq, Long memberSeq);

    void removeLike(Long postSeq, Long memberSeq);

    Integer countByPostSeq(Long postSeq);
}
