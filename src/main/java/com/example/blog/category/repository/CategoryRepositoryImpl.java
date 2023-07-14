package com.example.blog.category.repository;

import com.example.blog.category.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private JdbcTemplate template;

    public CategoryRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> findAll() {
        String sql = "select * from category";
        return template.query(sql, CategoriesRowWrapper());
    }

    private RowMapper<Category> CategoriesRowWrapper() {
        return (rs, rowNum) -> {
            Category category = new Category();
            category.setCategorySeq(rs.getLong(1));
            category.setCategoryName(rs.getString(2));
            category.setCategoryContent(rs.getString(3));
            return category;
        };
    }
}
