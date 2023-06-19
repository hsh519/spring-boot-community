package com.example.blog.service;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.repository.CommentRepository;
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
    public List<Comment> getCommentList(Long postSeq) {
        return commentRepository.findAll(postSeq);
    }

    @Override
    public Integer getCommentCnt(Long postSeq) {
        return commentRepository.commentCount(postSeq);
    }

    private void setMemberInfo(Comment comment, Member member) {
        comment.setMemberSeq(member.getMemberSeq());
        comment.setComment_writer(member.getMemberName());
    }

    private static void setRegisterAndUpdateDate(Comment comment) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        comment.setComment_register(date);
        comment.setComment_update(date);
    }
}
