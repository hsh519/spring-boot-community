package com.example.blog.post.domain;

import com.example.blog.member.domain.Member;
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
    private String postUpdate;

    private int postLike;

    public Post(Long categorySeq, String postName, String postContent) {
        this.categorySeq = categorySeq;
        this.postName = postName;
        this.postContent = postContent;
    }

    public void setMemberAndDate(Member member, String date) {
        this.setMemberSeq(member.getMemberSeq());
        this.setPostWriter(member.getMemberName());
        this.setPostUpdate(date);
    }

}
