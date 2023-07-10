package com.example.blog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long memberSeq;

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email
    @Size(max = 100)
    private String memberEmail;

    @NotEmpty(message = "패스워드는 필수입니다.")
    @Size(max = 100)
    private String memberPw;

    @NotEmpty(message = "패스워드 확인란은 필수입니다.")
    @Size(max = 100)
    private String checkPw;

    @NotEmpty(message = "이름은 필수입니다.")
    @Size(max = 50)
    private String memberName;

    public Member(String memberEmail, String memberPw) {
        this.memberEmail = memberEmail;
        this.memberPw = memberPw;
    }

    public Member(String memberEmail, String memberPw, String checkPw, String memberName) {
        this.memberEmail = memberEmail;
        this.memberPw = memberPw;
        this.checkPw = checkPw;
        this.memberName = memberName;
    }
}
