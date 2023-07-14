package com.example.blog.repository;

import com.example.blog.category.domain.Category;
import com.example.blog.category.repository.CategoryRepository;
import com.example.blog.category.repository.CategoryRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

import static com.example.blog.connection.ConnectionConst.*;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        CategoryRepository categoryRepository() { return new CategoryRepositoryImpl(dataSource()); }

        @Bean
        DataSource dataSource() { return new DriverManagerDataSource(URL, USERNAME, PASSWORD); }
    }

    @Test
    void 카테고리목록조회() {
        List<Category> categories = categoryRepository.CategoryAll();
        System.out.println("categories = " + categories.toString());
    }
}
