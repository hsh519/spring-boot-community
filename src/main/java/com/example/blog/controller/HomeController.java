package com.example.blog.controller;


import com.example.blog.category.domain.Category;
import com.example.blog.category.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController {

    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        List<Category> categoryList = categoryService.getCategories();
        model.addAttribute("categories", categoryList);

        HttpSession session = request.getSession(false);
        if (session == null) {
            model.addAttribute("sessionId", false);
        } else {
            model.addAttribute("sessionId", true);
        }
        return "index";
    }



}
