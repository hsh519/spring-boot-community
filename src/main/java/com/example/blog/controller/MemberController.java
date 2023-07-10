package com.example.blog.controller;

import com.example.blog.member.domain.Member;
import com.example.blog.member.domain.MemberLoginForm;
import com.example.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("member", new Member());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute Member member, BindingResult bindingResult, Model model) {

        // Bean Validation에 위반할 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            return "/register";
        }
        // 중복되는 아이디일 경우
        if (memberService.countEmail(member.getMemberEmail()) != 0) {
            bindingResult.rejectValue("memberId", "duplicateId");
        }
        // 비밀번호가 일치하지 않을 경우 (memberPw != checkPw)
        else if (!member.getMemberPw().equals(member.getCheckPw())) {
            bindingResult.rejectValue("checkPw","NotCorrectPw");
        }
        // 중복되는 닉네임일 경우
        else if (memberService.countName(member.getMemberName()) != 0) {
            bindingResult.rejectValue("memberName","duplicateName");
        }

        // 위 3가지 사항에 위반할 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            return "/register";
        }

        // 회원 저장
        memberService.register(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new MemberLoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") MemberLoginForm member, BindingResult bindingResult, Model model, HttpServletRequest request) {
        // Bean Validation에 위반할 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            return "login";
        }

        String requestURI = request.getParameter("requestURI");
        requestURI = (requestURI == null) ? "/" : requestURI;

        String loginEmail = member.getMemberEmail();

        // 일치하는 이메일이 없는 경우
        if (memberService.countEmail(loginEmail) == 0) {
            bindingResult.reject("NotFoundAccount",null,"일치하는 이메일이 없습니다.");
            model.addAttribute("member", member);
            return "login";
        }

        Member loginMember = memberService.login(loginEmail);

        // 패스워드가 틀린 경우
        if (! member.getMemberPw().equals(loginMember.getMemberPw())) {
            bindingResult.reject("NotCorrectPw", null, "비밀번호가 일치하지 않습니다.");
            model.addAttribute("member", member);
            return "login";
        }

        // 세션에 담기
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return "redirect:" + requestURI;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);
        return "myPage";
    }
}
