package com.project.blog.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String userName;
    private String title;
    private String content;
    private String password;
}
