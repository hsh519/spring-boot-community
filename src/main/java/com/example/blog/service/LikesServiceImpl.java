package com.example.blog.service;

import com.example.blog.repository.LikesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class LikesServiceImpl implements LikesService {

    private LikesRepository likesRepository;

    @Override
    @Transactional
    public void updateLike(Long postSeq, Long memberSeq) {
        if (likesRepository.isLike(postSeq, memberSeq) == 1) {
            likesRepository.removeLike(postSeq, memberSeq);
        } else {
            likesRepository.addLike(postSeq, memberSeq);
        }
    }

    @Override
    public Integer countLikes(Long postSeq) {
        return likesRepository.countByPostSeq(postSeq);
    }

    @Override
    public Boolean isLoginMemberLike(Long postSeq, Long memberSeq) {
        if (likesRepository.isLike(postSeq, memberSeq) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
