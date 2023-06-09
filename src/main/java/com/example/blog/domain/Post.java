package com.example.blog.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Post {

    @NotNull
    private Long postSeq;

    @NotNull
    private Long memberSeq;

    @NotNull
    private Long categorySeq;

    @NotBlank
    @Size(max = 50)
    private String postName;

    @NotNull
    private String postWriter;

    @NotBlank
    @Size(max = 1000)
    private String postContent;

    @NotNull
    private String postRegister;

    @NotNull
    private String postUpdate;

    @Size(max = 100)
    private String postTag;

    public Post(Long categorySeq, String postName, String postContent) {
        this.categorySeq = categorySeq;
        this.postName = postName;
        this.postContent = postContent;
    }
}
