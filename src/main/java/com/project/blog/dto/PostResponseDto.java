package com.project.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.blog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private final Boolean result;
    private final Long id;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public PostResponseDto(Post post) {
        this.result = null;
        this.id = post.getId();
        this.userName = post.getUserName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

    public PostResponseDto(Boolean result) {
        this.result = result;
        this.id = null;
        this.userName = null;
        this.title = null;
        this.content = null;
        this.createdAt = null;
    }
}
