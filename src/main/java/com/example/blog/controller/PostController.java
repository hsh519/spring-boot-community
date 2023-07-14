package com.example.blog.controller;

import com.example.blog.comment.domain.Comment;
import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;
import com.example.blog.post.domain.PostForm;
import com.example.blog.category.service.CategoryService;
import com.example.blog.comment.service.CommentService;
import com.example.blog.likes.service.LikesService;
import com.example.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final LikesService likesService;

    private final Long PAGE_CNT = 5L;

    @GetMapping("/post")
    public String postForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        model.addAttribute("categoriesName", categoryService.getCategories());
        return "post";
    }

    @PostMapping("/post")
    public String post(@Validated @ModelAttribute PostForm postForm,
                       BindingResult bindingResult,
                       @RequestParam Long categorySeq,
                       HttpServletRequest request,
                       Model model) {

        // Bean Validation에 위반될 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("postForm", postForm);
            return "post";
        }

        Post post = new Post(categorySeq, postForm.getPostName(), postForm.getPostContent());
        postService.post(post, getLoginMember(request));
        return "redirect:/";
    }

    @GetMapping("/postList")
    public String postList(Model model,
                           @RequestParam int page,
                           @RequestParam String searchKeyword,
                           HttpServletRequest request) {

        // 로그인 여부에 따른 메뉴 변경
        Member loginMember = getLoginMember(request);

        if (loginMember == null) {
            model.addAttribute("sessionId", false);
        } else {
            model.addAttribute("sessionId", true);
        }

        // 시작과 끝 페이지 설정
        Long startSeq = (page-1) * PAGE_CNT;
        Integer PostsCnt = postService.getPostsCnt(null, searchKeyword);
        int endPage = getEndPage(PostsCnt);

        // 이전, 다음 페이지의 유무
        Boolean prevPage = (page == 1) ? false : true;
        Boolean nextPage = (page == endPage || endPage == 0) ? false : true;

        // 페이지, 검색 결과에 맞는 게시물들 가져오기
        List<Post> postList = postService.getPosts(null, startSeq, PAGE_CNT, searchKeyword);

        model.addAttribute("postList", postList);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("curPage", page);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("endPage", endPage);
        return "postList";
    }

    @GetMapping("/post/{postSeq}")
    public String getPost(@PathVariable Long postSeq, Model model, HttpServletRequest request) {
        // 로그인한 회원 정보 가져오기
        Member loginMember = getLoginMember(request);

        Post findPost = postService.getPost(postSeq);

        // 로그인한 회원이 해당 게시물에 좋아요를 눌렀는지 체크
        Boolean like = likesService.isLike(postSeq, loginMember.getMemberSeq());

        // 해당 게시물에 달린 댓글 가져오기
        List<Comment> commentList = commentService.getComments(postSeq);

        model.addAttribute("post", findPost);
        model.addAttribute("isLike", like);
        model.addAttribute("likeCnt", likesService.likesCnt(postSeq));
        model.addAttribute("commentCnt", commentList.size());
        model.addAttribute("commentList", commentList);
        model.addAttribute("comment", new Comment());
        return "postOne";
    }

    @GetMapping("/update/{postSeq}")
    public String postUpdateForm(@PathVariable Long postSeq, Model model, HttpServletRequest request) {
        // 수정할 때 필요한 게시물 부분 가져오기
        Post findPost = postService.getPost(postSeq);

        // 로그인한 회원 정보 가져오기
        Member loginMember = getLoginMember(request);

        // 로그인한 회원과 게시물 작성자가 같으면 수정 페이지로 이동
        if (loginMember.getMemberSeq() == findPost.getMemberSeq()) {
            model.addAttribute("postForm", findPost);
            model.addAttribute("categoriesName", categoryService.getCategories());
            return "updatePost";
        }
        return "error";
    }

    @PostMapping("/update/{postSeq}")
    public String postUpdate(@Validated @ModelAttribute PostForm postForm,
                             BindingResult bindingResult,
                             @PathVariable Long postSeq,
                             @RequestParam Long categorySeq,
                             Model model) {

        // Bean Validation에 위반될 경우
        if (bindingResult.hasErrors()) {
            model.addAttribute("postForm", postForm);
            return "updatePost";
        }

        Post post = new Post(categorySeq, postForm.getPostName(), postForm.getPostContent());
        postService.updatePost(post, postSeq);

        return "redirect:/myPost?page=1&searchKeyword=";
    }

    @GetMapping("/delete/{postSeq}")
    public String postDelete(@PathVariable Long postSeq, HttpServletRequest request) {
        // 삭제할 게시물 가져오기
        Post post = postService.getPost(postSeq);

        // 로그인한 회원 정보 가져오기
        Member loginMember = getLoginMember(request);

        // 로그인한 회원과 게시물 작성자가 동일하면 삭제 -> 댓글, 좋아요 삭제 후 게시물 삭제
        if (loginMember.getMemberSeq() == post.getMemberSeq()) {
            postService.deletePost(postSeq);
            return "redirect:/myPost?page=1&searchKeyword=";
        }
        return "redirect:/error";
    }

    @GetMapping("/myPost")
    public String myPost(@RequestParam("searchKeyword") String searchKeyword,
                         HttpServletRequest request,
                         @RequestParam int page,
                         Model model) {
        // 로그인한 회원 정보 가져오기
        Member loginMember = getLoginMember(request);
        Long memberSeq = loginMember.getMemberSeq();

        // 회원이 작성한 게시물 개수로 시작, 끝 페이지 결정
        Long startSeq =  (page-1) * PAGE_CNT;
        Integer postsCnt = postService.getMyPostsCnt(memberSeq, searchKeyword);
        int endPage = getEndPage(postsCnt);

        // 이전, 다음 페이지 유무
        Boolean prevPage = (page == 1) ? false : true;
        Boolean nextPage = (page == endPage || endPage == 0) ? false : true;

        // 회원이 작성한 게시물들 가져오기
        List<Post> postList = postService.getMyPosts(memberSeq, searchKeyword, startSeq, PAGE_CNT);

        model.addAttribute("postList", postList);
        model.addAttribute("curPage", page);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("endPage", endPage);
        return "myPost";
    }

    @GetMapping("category/{categorySeq}")
    public String postListInCategory(@PathVariable Long categorySeq,
                                     @RequestParam int page,
                                     @RequestParam String searchKeyword,
                                     Model model) {
        // 카테고리별 게시물 개수로 시작, 끝 페이지 결정
        Long startSeq =  (page-1) * PAGE_CNT;
        Integer postsCnt = postService.getPostsCnt(categorySeq, searchKeyword);
        int endPage = getEndPage(postsCnt);

        // 이전, 다음 페이지의 유무
        Boolean prevPage = (page == 1) ? false : true;
        Boolean nextPage = (page == endPage || endPage == 0) ? false : true;

        // 카테고리별 게시물 가져오기
        List<Post> postList = postService.getPosts(categorySeq, startSeq, PAGE_CNT, searchKeyword);
        System.out.println("postList = " + postList);

        model.addAttribute("postList", postList);
        model.addAttribute("categorySeq", categorySeq);
        model.addAttribute("curPage", page);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("searchKeyword", searchKeyword);
        return "categoryPostList";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    private int getEndPage(Integer allPostCnt) {
        if (allPostCnt % 5 == 0) {
            return allPostCnt / 5;
        }
        return (int) ((double) allPostCnt / 5) + 1;
    }

    private static Member getLoginMember(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Member loginMember = (Member) session.getAttribute("loginMember");
        return loginMember;
    }
}
