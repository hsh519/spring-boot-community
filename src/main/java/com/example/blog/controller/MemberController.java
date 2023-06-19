package com.example.blog.controller;

import com.example.blog.domain.Member;
import com.example.blog.domain.MemberLoginForm;
import com.example.blog.domain.MemberRegisterForm;
import com.example.blog.domain.Post;
import com.example.blog.repository.MemberRepository;
import com.example.blog.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class MemberController {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberRegisterForm", new MemberRegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute MemberRegisterForm memberRegisterForm, BindingResult bindingResult, Model model) {
        // Bean Validation에 위반할 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("memberRegisterForm", memberRegisterForm);
            return "/register";
        }
        // 중복되는 아이디일 경우
        if (memberRepository.findById(memberRegisterForm.getMemberId()) != null) {
            bindingResult.rejectValue("memberId", "duplicateId");
        }
        // 비밀번호가 일치하지 않을 경우 (memberPw != checkPw)
        else if (!memberRegisterForm.getMemberPw().equals(memberRegisterForm.getCheckPw())) {
            bindingResult.rejectValue("checkPw","NotCorrectPw");
        }
        // 중복되는 닉네임일 경우
        else if (memberRepository.findByName(memberRegisterForm.getMemberName()) != null) {
            bindingResult.rejectValue("memberName","duplicateName");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("memberRegisterForm", memberRegisterForm);
            return "/register";
        }

        Member member = new Member(memberRegisterForm.getMemberId(), memberRegisterForm.getMemberPw(), memberRegisterForm.getMemberName());
        memberService.register(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("memberLoginForm", new MemberLoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute MemberLoginForm memberLoginForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("memberLoginForm", memberLoginForm);
            return "login";
        }

        Member member = new Member(memberLoginForm.getMemberId(), memberLoginForm.getMemberPw());
        Member loginMember = memberService.login(member);

        if (loginMember == null) {
            bindingResult.reject("NotFoundAccount",null,"일치하는 아이디가 없습니다.");
            model.addAttribute("memberLoginForm", memberLoginForm);
            return "login";
        } else if (! loginMember.getMemberPw().equals(member.getMemberPw())) {
            bindingResult.reject("NotCorrectPw", null, "비밀번호가 일치하지 않습니다.");
            model.addAttribute("memberLoginForm", memberLoginForm);
            return "login";
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/myPage")
    public String myPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        List<Post> posts = memberService.myPost(loginMember);
        model.addAttribute("postList", posts);
        return "myPage";
    }
}
