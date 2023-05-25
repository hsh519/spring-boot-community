package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Slf4j
public class CommentController {

    private CommentService commentService;
    private PostService postService;

    @PostMapping("/comment")
    public String comment(@ModelAttribute Comment comment, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

        log.info("comment={}", comment.toString());
        commentService.comment(comment, loginMember);


        return "redirect:/post/" + comment.getPostSeq();
    }
}
