package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
import com.example.blog.domain.PostForm;
import com.example.blog.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Override
    @Transactional
    public void post(Post post, Member member) {

        String date = getDateToString();
        post.setMemberSeq(member.getMemberSeq());
        post.setPostWriter(member.getMemberName());
        post.setPostRegister(date);
        post.setPostUpdate(date);

        postRepository.save(post);
    }

    @Override
    @Transactional
    public List<Post> getPostList() {
        return postRepository.postAll();
    }

    @Override
    public List<Post> getPostListInCategory(Long categorySeq) {
        return postRepository.findByCategory(categorySeq);
    }

    @Override
    @Transactional
    public Post getPost(Long postSeq) {
        return postRepository.findBySeq(postSeq);
    }

    @Override
    @Transactional
    public void updatePost(PostForm postForm, Long postSeq) {
        postRepository.update(postForm, postSeq);
    }

    @Override
    @Transactional
    public void deletePost(Long postSeq) {
        postRepository.delete(postSeq);
    }

    private static String getDateToString() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
