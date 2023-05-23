package com.example.blog.repository;

import com.example.blog.connection.ConnectionConst;
import com.example.blog.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

import static com.example.blog.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;

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

    @Test
    void 게시물하나조회() {
        Post post = new Post(100L, 1L, "title", "test", "content", "2022-05-22 15:08:00", "2022-05-22 15:08:00", null);
        postRepository.save(post);

        Post findPost = postRepository.findBySeq(100L);

        assertThat(findPost.getPostName()).isEqualTo(post.getPostName());
    }
}
