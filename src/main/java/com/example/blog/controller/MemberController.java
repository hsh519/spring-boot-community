package com.example.blog.controller;

import com.example.blog.domain.Member;
import com.example.blog.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        memberService.register(member);
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new Member());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Member member, Model model, HttpServletRequest request) {
        Member loginMember = memberService.login(member);
        if (loginMember == null | !(member.getMemberPw().equals(loginMember.getMemberPw()))) {
            model.addAttribute("member", new Member());
            return "login";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", loginMember);
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }


}
