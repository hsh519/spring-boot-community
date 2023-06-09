package com.example.blog.service;

import com.example.blog.domain.Category;
import com.example.blog.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CateogoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.CategoryAll();
    }
}
