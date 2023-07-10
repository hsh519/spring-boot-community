package com.example.blog.post.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {

    private Long postSeq;

    @NotBlank
    @Size(max = 50)
    private String postName;

    @NotBlank
    @Size(max = 1000)
    private String postContent;
}
