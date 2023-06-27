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
    public List<Post> getPostList(Long startSeq, Long pageCnt) {
        return postRepository.postAll(startSeq, pageCnt);
    }

    @Override
    public List<Post> getPostListInCategory(Long categorySeq, Long startSeq, Long pageCnt) {
        return postRepository.findByCategory(categorySeq, startSeq, pageCnt);
    }

    @Override
    public Post getPost(Long postSeq, Boolean callUpdate) {
        if (callUpdate) {
            postRepository.postViewPlus(postSeq);
        }
        return postRepository.findBySeq(postSeq);
    }


    @Override
    public void updatePost(Post post, Long postSeq) {
        postRepository.update(post, postSeq);
    }

    @Override
    public void deletePost(Long postSeq) {
        postRepository.delete(postSeq);
    }

    @Override
    public Integer getPostCnt() {
        return postRepository.postCnt();
    }

    @Override
    public List<Post> search(String searchKeyword, Long startSeq, Long pageCnt) {
        return postRepository.findBySearch(searchKeyword, startSeq, pageCnt);
    }

    @Override
    public Integer getPostCntByCategory(Long categorySeq) {
        return postRepository.postCntByCategory(categorySeq);
    }

    @Override
    public Integer getPostCntBySearchKeyword(String searchKeyword) {
        return postRepository.postCntBySearchKeyword(searchKeyword);
    }

    private static String getDateToString() {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
