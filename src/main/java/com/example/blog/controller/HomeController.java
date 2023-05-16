package com.example.blog.controller;

import com.example.blog.domain.Member;
import com.example.blog.repository.MemberRepository;
import com.example.blog.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@AllArgsConstructor
public class HomeController {

    private MemberService memberService;

    @RequestMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Member member) {
        try {
            member.setMemberSeq(2L);
            memberService.register(member);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "index";
    }

    @RequestMapping("/contact")
    public String contactForm() {
        return "contact";
    }
}
