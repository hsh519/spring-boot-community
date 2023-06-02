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

    private PostService postService;

    @GetMapping("/")
    public String home() {
        return "index";
    }



}
