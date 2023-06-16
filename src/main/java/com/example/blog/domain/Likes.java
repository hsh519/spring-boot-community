package com.example.blog.domain;

import lombok.Data;

@Data
public class Likes {
    private Long likeSeq;
    private Long memberSeq;
    private Long postSeq;
}
