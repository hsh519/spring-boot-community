package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void register(Member member) {
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public Member login(Member member) {
        return memberRepository.findById(member.getMemberId());
    }
}
