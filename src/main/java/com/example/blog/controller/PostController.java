package com.example.blog.controller;

import com.example.blog.domain.*;
import com.example.blog.service.CategoryService;
import com.example.blog.service.CommentService;
import com.example.blog.service.LikesService;
import com.example.blog.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class PostController {

    private PostService postService;
    private CategoryService categoryService;
    private CommentService commentService;
    private LikesService likesService;

    @GetMapping("/post")
    public String postForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("categoriesName", categoryService.getCategoryList());
        return "post";
    }

    @PostMapping("/post")
    public String post(@Validated @ModelAttribute PostForm postForm, BindingResult bindingResult, @RequestParam Long categorySeq, HttpServletRequest request, Model model) {
        log.info("categorySeq={}", categorySeq);
        // Bean Validation 에 위반될 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("postForm", postForm);
            return "post";
        }

        Post post = new Post(categorySeq, postForm.getPostName(), postForm.getPostContent());
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

    @GetMapping("/postList/{postSeq}")
    public String getPost(@PathVariable Long postSeq, Model model, HttpServletRequest request) {
        Boolean loginMemberLike = false;
        HttpSession session = request.getSession(false);
        if (session == null) {
            model.addAttribute("loginMember", null);
        } else {
            Member loginMember = (Member) session.getAttribute("loginMember");
            loginMemberLike = likesService.isLoginMemberLike(postSeq, loginMember.getMemberSeq());

            model.addAttribute("loginMember", loginMember);
        }

        Post findPost;
        if (model.containsAttribute("callUpdate")) {
            findPost = postService.getPost(postSeq, false);
        } else {
            findPost = postService.getPost(postSeq, true);
        }

        List<Comment> commentList = commentService.getCommentList(postSeq);

        log.info("isLike={}", loginMemberLike);
        model.addAttribute("isLike", loginMemberLike);
        model.addAttribute("likeCnt", likesService.countLikes(postSeq));
        model.addAttribute("commentCnt", commentList.size());
        model.addAttribute("commentList", commentList);
        model.addAttribute("post", findPost);
        model.addAttribute("comment", new Comment());
        return "postOne";
    }

    @GetMapping("/update/{postSeq}")
    public String postUpdateForm(@PathVariable Long postSeq, Model model, HttpServletRequest request) {
        // 로그인 사용자가 수정 버튼이 아닌 url로 접근했을 때, 게시 글 작성자와 일치하는 지 확인
        Post findPost = postService.getPost(postSeq, false);

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember.getMemberSeq() == findPost.getMemberSeq()) {
            model.addAttribute("postForm", findPost);
            model.addAttribute("categoriesName", categoryService.getCategoryList());
            return "updatePost";
        }
        else {
            return "redirect:/error";
        }
    }

    @PostMapping("/update/{postSeq}")
    public String postUpdate(@Validated @ModelAttribute PostForm postForm, BindingResult bindingResult,
                             @PathVariable Long postSeq, @RequestParam Long categorySeq, Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("postForm", postForm);
            return "updatePost";
        }
        Post post = new Post(categorySeq, postForm.getPostName(), postForm.getPostContent());
        postService.updatePost(post, postSeq);
        // 리다이렉트 시 조회수가 증가하지 않도록 하는 장치
        redirectAttributes.addFlashAttribute("callUpdate", true);
        return "redirect:/postList/{postSeq}";
    }

    @GetMapping("/delete/{postSeq}")
    public String postDelete(@PathVariable Long postSeq, HttpServletRequest request) {
        Post post = postService.getPost(postSeq, false);

        HttpSession session = request.getSession(false);
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember.getMemberSeq() == post.getMemberSeq()) {
            postService.deletePost(postSeq);
            return "redirect:/";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("category/{categorySeq}")
    public String postListInCategory(@PathVariable Long categorySeq, Model model) {
        List<Post> postList = postService.getPostListInCategory(categorySeq);
        model.addAttribute("postList", postList);
        return "postList";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
