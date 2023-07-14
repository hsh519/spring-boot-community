package com.example.blog.post.repository;

import com.example.blog.post.domain.Post;

import java.util.List;

public interface PostRepository {

    void save(Post post);

    Integer countAllByKeyword(String searchKeyword);

    List<Post> findAllByKeyword(Long startSeq, Long pageCnt, String searchKeyword);

    Post findBySeq(Long postSeq);

    void update(Post post, Long postSeq);

    void delete(Long postSeq);

    Integer countMyPostsByKeyword(Long memberSeq, String searchKeyword);

    List<Post> findMyPostsByKeyword(Long memberSeq, String searchKeyword, Long startSeq, Long pageCnt);

    List<Post> findAllByCategorySeqAndKeyword(Long categorySeq, Long startSeq, Long pageCnt, String searchKeyword);

    Integer countAllByCategoryAndKeyword(Long categorySeq, String searchKeyword);

}
