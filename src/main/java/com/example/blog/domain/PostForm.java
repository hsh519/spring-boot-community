package com.example.blog.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostForm {

    @NotBlank
    @Size(max = 50)
    private String postName;

    @NotBlank
    @Size(max = 1000)
    private String postContent;
}
