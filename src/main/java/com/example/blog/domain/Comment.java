package com.example.blog.domain;


import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @NotNull
    private Long commentSeq;
    @NotNull
    private Long postSeq;
    @NotNull
    private Long memberSeq;
    @NotNull
    @Size(max = 1000)
    private String comment_content;
    @NotNull
    @Size(max = 50)
    private String comment_writer;
    @NotNull
    @Size(max = 50)
    private String comment_register;
    @NotNull
    @Size(max = 50)
    private String comment_update;
}
