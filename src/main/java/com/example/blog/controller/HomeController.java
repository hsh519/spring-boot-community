package com.example.blog.controller;

import com.example.blog.domain.Member;
import com.example.blog.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController {

    private MemberService memberService;

    @RequestMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        memberService.register(member);

        return "index";
    }

    @RequestMapping("/login")
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
        return "index";
    }

    @RequestMapping("/contact")
    public String contactForm() {
        return "contact";
    }
}
