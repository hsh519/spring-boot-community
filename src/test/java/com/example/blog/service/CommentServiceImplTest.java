package com.example.blog.service;

import com.example.blog.comment.domain.Comment;
import com.example.blog.comment.service.CommentService;
import com.example.blog.comment.service.CommentServiceImpl;
import com.example.blog.member.domain.Member;
import com.example.blog.comment.repository.CommentRepository;
import com.example.blog.comment.repository.CommentRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static com.example.blog.connection.ConnectionConst.*;

@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @TestConfiguration
    static class testConfig {
        @Bean
        DataSource dataSource() { return new DriverManagerDataSource(URL, USERNAME, PASSWORD); }
        @Bean
        CommentRepository commentRepository() { return new CommentRepositoryImpl(dataSource()); }
        @Bean
        CommentService commentService() { return new CommentServiceImpl(commentRepository()); }
    }

    @Test
    void 댓글저장() {
        Member member = new Member(1L,"test@test.com","1234","123");
        Comment comment = new Comment();
        comment.setCommentSeq(100L);
        comment.setPostSeq(2L);
        comment.setComment_content("test-content");

        commentService.comment(comment, member);
    }
}
