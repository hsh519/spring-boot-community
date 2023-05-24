package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @GetMapping("/post")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "postForm";
    }

    @PostMapping("/post")
    public String post(@ModelAttribute Post post, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        postService.post(post, loginMember);
        return "redirect:/";
    }

    @GetMapping("/post/{postSeq}")
    public String getPost(@PathVariable Long postSeq, HttpServletRequest request, Model model) {
        Post findPost = postService.getPost(postSeq);
        model.addAttribute("post", findPost);
        model.addAttribute("comment", new Comment());
        return "/post";
    }

    @GetMapping("/post/update/{postSeq}")
    public String postUpdateForm(@PathVariable Long postSeq, HttpServletRequest request, Model model) {
        Post findPost = postService.getPost(postSeq);
        model.addAttribute("post", findPost);
        return "/postForm";

    }

    @PostMapping("/post/update/{postSeq}")
    public String postUpdate(@PathVariable Long postSeq, @ModelAttribute Post post, HttpServletRequest request, Model model) {
        postService.updatePost(post, postSeq);
        return "redirect:/post/{postSeq}";
    }

    @GetMapping("/post/delete/{postSeq}")
    public String postDelete(@PathVariable Long postSeq, HttpServletRequest request) {
        postService.deletePost(postSeq);
        return "redirect:/";
    }
}
