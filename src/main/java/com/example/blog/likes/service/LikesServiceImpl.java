package com.example.blog.likes.service;

import com.example.blog.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;

    @Override
    public Boolean isLike(Long postSeq, Long memberSeq) {
        if (likesRepository.getLike(postSeq, memberSeq) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Integer likesCnt(Long postSeq) {
        return likesRepository.countLikes(postSeq);
    }

    @Override
    @Transactional
    public void updateLike(Long postSeq, Long memberSeq) {
        if (likesRepository.getLike(postSeq, memberSeq) == 1) {
            likesRepository.removeLike(postSeq, memberSeq);
        } else {
            likesRepository.addLike(postSeq, memberSeq);
        }
    }


}
