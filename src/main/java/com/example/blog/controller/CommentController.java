package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Slf4j
public class CommentController {

    private CommentService commentService;
    private PostService postService;

    @PostMapping("/comment")
    public String comment(@ModelAttribute Comment comment, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (comment.getComment_content().isBlank()) {
            redirectAttributes.addFlashAttribute("errors", "댓글을 입력해주세요.");
            redirectAttributes.addFlashAttribute("callUpdate", true);
            return "redirect:/postList/" + comment.getPostSeq();
        }
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

        commentService.comment(comment, loginMember);
        redirectAttributes.addFlashAttribute("callUpdate", true);

        return "redirect:/postList/" + comment.getPostSeq();
    }
}
