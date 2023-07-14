package com.example.blog.category.repository;

import com.example.blog.category.domain.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
