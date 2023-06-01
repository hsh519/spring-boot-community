package com.example.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {

    @NotNull
    private Long memberSeq;

    @NotBlank
    @Email
    @Size(max = 100)
    private String memberId;

    @NotBlank
    @Size(max = 100)
    private String memberPw;

    @NotBlank
    @Size(max = 50)
    private String memberName;

    public Member(String memberId, String memberPw) {
        this.memberId = memberId;
        this.memberPw = memberPw;
    }

    public Member(String memberId, String memberPw, String memberName) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.memberName = memberName;
    }
}
