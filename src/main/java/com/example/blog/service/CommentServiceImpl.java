package com.example.blog.service;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;


    @Override
    @Transactional
    public void comment(Comment comment, Member member) {
        setMemberInfo(comment, member);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public List<Comment> getCommentList(Long postSeq) {
        return commentRepository.findAll(postSeq);
    }

    private void setMemberInfo(Comment comment, Member member) {
        comment.setMemberSeq(member.getMemberSeq());
        comment.setComment_writer(member.getMemberName());
    }
}
