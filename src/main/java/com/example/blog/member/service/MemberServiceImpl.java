package com.example.blog.member.service;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;
import com.example.blog.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void register(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member login(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Post> myPost(Member member, Long startSeq, Long pageCnt) {
        return memberRepository.getMyPost(member.getMemberSeq(), startSeq, pageCnt);
    }

    @Override
    public Integer getMyPostCnt(Long postId) {
        return memberRepository.myPostCnt(postId);
    }

    @Override
    public Integer countEmail(String email) {
        return memberRepository.countByEmail(email);
    }

    @Override
    public Integer countName(String name) {
        return memberRepository.countByName(name);
    }
}
