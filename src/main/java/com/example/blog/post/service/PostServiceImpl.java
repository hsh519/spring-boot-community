package com.example.blog.post.service;

import com.example.blog.member.domain.Member;
import com.example.blog.post.domain.Post;
import com.example.blog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public void post(Post post, Member member) {
        String date = getDateToString();
        post.setMemberAndDate(member, date);
        postRepository.save(post);
    }

    @Override
    public Integer getPostsCnt(Long categorySeq, String searchKeyword) {
        if (categorySeq == null) {
            return postRepository.countAllByKeyword(searchKeyword);
        }
        return postRepository.countAllByCategoryAndKeyword(categorySeq, searchKeyword);
    }

    @Override
    public List<Post> getPosts(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword) {
        if (categorySeq == null) {
            return postRepository.findAllByKeyword(startSeq, pageCnt, searchKeyword);
        }
        return postRepository.findAllByCategorySeqAndKeyword(categorySeq, startSeq, pageCnt, searchKeyword);
    }

    @Override
    public Post getPost(Long postSeq) {
        return postRepository.findBySeq(postSeq);
    }

    @Override
    @Transactional
    public void updatePost(Post post, Long postSeq) {
        post.setPostUpdate(getDateToString());
        postRepository.update(post, postSeq);
    }

    @Override
    public void deletePost(Long postSeq) {
        postRepository.delete(postSeq);
    }

    @Override
    public Integer getMyPostsCnt(Long memberSeq, String searchKeyword) {
        return postRepository.countMyPostsByKeyword(memberSeq, searchKeyword);
    }

    @Override
    public List<Post> getMyPosts(Long memberSeq, String searchKeyword, Long startSeq, Long pageCnt) {
        return postRepository.findMyPostsByKeyword(memberSeq, searchKeyword, startSeq, pageCnt);
    }

    private static String getDateToString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}
