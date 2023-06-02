package com.example.blog.controller;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private CommentService commentService;
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
    public String postList(Model model) {
        List<Post> postList = postService.getPostList();
        model.addAttribute("postList", postList);
        return "postList";
    }

    @GetMapping("/post/{postSeq}")
    public String getPost(@PathVariable Long postSeq, Model model) {
        Post findPost = postService.getPost(postSeq);
        List<Comment> commentList = commentService.getCommentList(postSeq);

        model.addAttribute("post", findPost);
        model.addAttribute("comment", new Comment());
        model.addAttribute("comments", commentList);
        return "/post";
    }

    @GetMapping("/post/update/{postSeq}")
    public String postUpdateForm(@PathVariable Long postSeq, HttpServletRequest request, Model model) {
        Post findPost = postService.getPost(postSeq);
        model.addAttribute("post", findPost);
        return "postList";

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
