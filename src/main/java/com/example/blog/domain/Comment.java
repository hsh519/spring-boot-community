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

    private Long commentSeq;
    private Long postSeq;
    private Long memberSeq;
    private String comment_content;
    private String comment_writer;
    private String comment_register;
    private String comment_update;
}
