package com.example.blog.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Category {

    @NotNull
    private Long categorySeq;

    @NotBlank
    @Size(max = 50)
    private String categoryName;

    @NotBlank
    @Size(max = 100)
    private String categoryContent;
}
