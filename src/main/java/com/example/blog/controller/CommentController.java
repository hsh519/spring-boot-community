package com.example.blog.controller;

import com.example.blog.comment.domain.Comment;
import com.example.blog.member.domain.Member;
import com.example.blog.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public String comment(@Validated @ModelAttribute Comment comment,
                          RedirectAttributes redirectAttributes,
                          HttpServletRequest request,
                          BindingResult bindingResult) {
        // 댓글 내용이 없을 경우
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", "댓글을 입력해주세요.");
            return "redirect:/post/" + comment.getPostSeq();
        }

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");

        commentService.comment(comment, loginMember);

        return "redirect:/post/" + comment.getPostSeq();
    }
}
