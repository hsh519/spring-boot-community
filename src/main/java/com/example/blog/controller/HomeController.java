package com.example.blog.controller;


import com.example.blog.domain.Category;
import com.example.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class HomeController {

    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categories", categoryList);
        model.addAttribute("searchKeyword", "");
        return "index";
    }



}
