package com.example.blog.domain;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MemberLoginForm {
    @NotBlank
    @Email
    private String memberId;

    @NotBlank
    private String memberPw;
}
