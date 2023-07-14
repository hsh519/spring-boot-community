package com.example.blog.comment.service;

import com.example.blog.comment.domain.Comment;
import com.example.blog.member.domain.Member;
import com.example.blog.comment.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;


    @Override
    @Transactional
    public void comment(Comment comment, Member member) {
        setMemberInfo(comment, member);
        setRegisterAndUpdateDate(comment);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long postSeq) {
        return commentRepository.findAll(postSeq);
    }

    private void setMemberInfo(Comment comment, Member member) {
        comment.setMemberSeq(member.getMemberSeq());
        comment.setCommentWriter(member.getMemberName());
    }

    private static void setRegisterAndUpdateDate(Comment comment) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date());
        comment.setCommentUpdate(date);
    }
}
