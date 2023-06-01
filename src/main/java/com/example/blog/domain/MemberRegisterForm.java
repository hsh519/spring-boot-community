package com.example.blog.domain;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MemberRegisterForm {

    @NotBlank
    @Email
    @Size(max = 100)
    private String memberId;

    @NotBlank
    @Size(max = 100)
    private String memberPw;

    @NotBlank
    @Size(max = 100)
    private String checkPw;

    @NotBlank
    @Size(max = 50)
    private String memberName;
}
