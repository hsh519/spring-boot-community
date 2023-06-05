package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PostController {

    private PostService postService;

    @GetMapping("/post")
    public String postForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "post";
    }

    @PostMapping("/post")
    public String post(@Validated @ModelAttribute PostForm postForm, BindingResult bindingResult, HttpServletRequest request, Model model) {
        // Bean Validation 에 위반될 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("postForm", postForm);
            return "post";
        }

        Post post = new Post(postForm.getPostName(), postForm.getPostContent());
        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        postService.post(post, loginMember);
        return "redirect:/";
    }

    @GetMapping("/postList")
    public String postList(@ModelAttribute("error") String error, Model model) {
        if (!error.isBlank()) {
            model.addAttribute("error", error);
        }
        List<Post> postList = postService.getPostList();
        model.addAttribute("postList", postList);
        return "postList";
    }

    @GetMapping("/postList/{postSeq}")
    public String getPost(@PathVariable Long postSeq, Model model) {
        Post findPost = postService.getPost(postSeq);
        log.info("post={}", findPost);
        model.addAttribute("post", findPost);
        return "postOne";
    }

    @GetMapping("/update/{postSeq}")
    public String postUpdateForm(@PathVariable Long postSeq, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 로그인 사용자가 수정 버튼이 아닌 url로 접근했을 때, 게시 글 작성자와 일치하는 지 확인
        Post findPost = postService.getPost(postSeq);

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember.getMemberSeq() == findPost.getMemberSeq()) {
            model.addAttribute("postForm", findPost);
            return "updatePost";
        }
        else {
            redirectAttributes.addFlashAttribute("error", "잘못된 접근");
            return "redirect:/postList";
        }
    }

    @PostMapping("/update/{postSeq}")
    public String postUpdate(@Validated @ModelAttribute PostForm postForm, BindingResult bindingResult, @PathVariable Long postSeq,  Model model) {
        log.info("postForm={}", postForm.toString());
        if (bindingResult.hasErrors()) {
            model.addAttribute("postForm", postForm);
            return "updatePost";
        }
        postService.updatePost(postForm, postSeq);
        return "redirect:/postList/{postSeq}";
    }

    @GetMapping("/delete/{postSeq}")
    public String postDelete(@PathVariable Long postSeq, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Post post = postService.getPost(postSeq);

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember.getMemberSeq() == post.getMemberSeq()) {
            postService.deletePost(postSeq);
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "잘못된 접근");
            return "redirect:/postList";
        }


    }
}
