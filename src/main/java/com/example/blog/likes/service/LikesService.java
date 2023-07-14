package com.example.blog.likes.service;

public interface LikesService {

    Boolean isLike(Long postSeq, Long memberSeq);

    Integer likesCnt(Long postSeq);

    void updateLike(Long postSeq, Long memberSeq);
}
