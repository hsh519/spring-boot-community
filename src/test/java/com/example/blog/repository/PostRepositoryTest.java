package com.example.blog.repository;

import com.example.blog.connection.ConnectionConst;
import com.example.blog.domain.Post;
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
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @TestConfiguration
    static class TestConfig {

        @Bean
        PostRepository postRepository() { return new PostRepositoryImpl(dataSource()); }

        @Bean
        DataSource dataSource() { return new DriverManagerDataSource(URL, USERNAME, PASSWORD); }
    }

    @Test
    void 게시물모두조회() {
        List<Post> posts = postRepository.postAll(3L);
        for (Post post : posts) {
            System.out.println("post = " + post.getPostName());
        }
    }
}
