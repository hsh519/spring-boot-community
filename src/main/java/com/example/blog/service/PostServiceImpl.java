package com.example.blog.service;

import com.example.blog.domain.Member;
import com.example.blog.domain.Post;
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

        post.setPostSeq(3L);
        post.setMemberSeq(member.getMemberSeq());
        post.setPostWriter(member.getMemberName());
        post.setPostRegister(date);
        post.setPostUpdate(date);

        postRepository.save(post);
    }

    @Override
    @Transactional
    public List<Post> getPostList(Member member) {
        return postRepository.postAll(member.getMemberSeq());
    }

    @Override
    @Transactional
    public Post getPost(Long postSeq) {
        return postRepository.findBySeq(postSeq);
    }

    @Override
    @Transactional
    public void updatePost(Post post, Long postSeq) {
        postRepository.update(post, postSeq);
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
