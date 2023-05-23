package com.example.blog.controller;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.service.MemberService;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController {

    private MemberService memberService;
    private PostService postService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member loginMember = (Member) session.getAttribute("loginMember");
            List<Post> postList = postService.getPostList(loginMember);
            model.addAttribute("postList", postList);
        }
        return "index";
    }

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
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/post")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "postForm";
    }

    @PostMapping("/post")
    public String post(@ModelAttribute Post post, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:login";
        } else {
            Member loginMember = (Member) session.getAttribute("loginMember");
            postService.post(post, loginMember);

            return "redirect:/";
        }
    }

    @GetMapping("/post/{postSeq}")
    public String getPost(@PathVariable Long postSeq, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        } else {
            Post findPost = postService.getPost(postSeq);
            model.addAttribute("post", findPost);
        }
        return "/post";
    }

    @GetMapping("/post/update/{postSeq}")
    public String postUpdateForm(@PathVariable Long postSeq, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        } else {
            Post findPost = postService.getPost(postSeq);
            model.addAttribute("post", findPost);
        }
        return "/postForm";

    }

    @PostMapping("/post/update/{postSeq}")
    public String postUpdate(@PathVariable Long postSeq, @ModelAttribute Post post, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        } else {
            log.info("post = {}", post.toString());
            postService.updatePost(post, postSeq);
        }
        return "redirect:/post/{postSeq}";
    }
}
