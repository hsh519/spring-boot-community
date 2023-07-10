package com.example.blog.member.domain;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginForm {
    @NotBlank
    @Email
    private String memberEmail;

    @NotBlank
    private String memberPw;
}
