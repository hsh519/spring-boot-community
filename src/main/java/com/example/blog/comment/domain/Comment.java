package com.example.blog.comment.domain;


import lombok.*;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class Comment {

    private Long commentSeq;

    private Long postSeq;

    private Long memberSeq;

    @NotEmpty
    private String commentContent;

    private String commentWriter;

    private String commentUpdate;
}
