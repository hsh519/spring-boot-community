package com.example.blog.service;

public interface LikesService {
    void updateLike(Long postSeq, Long memberSeq);

    Integer countLikes(Long postSeq);

    Boolean isLoginMemberLike(Long postSeq, Long memberSeq);
}
