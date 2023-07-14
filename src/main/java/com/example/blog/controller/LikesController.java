package com.example.blog.controller;

import com.example.blog.member.domain.Member;
import com.example.blog.likes.service.LikesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class LikesController {

    private LikesService likesService;

    @PostMapping("/likes/{postSeq}")
    public String Likes(@PathVariable Long postSeq, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        likesService.updateLike(postSeq, loginMember.getMemberSeq());

        redirectAttributes.addFlashAttribute("callUpdate", false);
        return "redirect:/post/" + postSeq;
    }

}
