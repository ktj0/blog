package com.project.blog.dto;

import com.project.blog.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String userName;
    private final String title;
    private final String content;

    public PostResponseDto(Post post) {
        this.id =post.getId();
        this.userName =post.getUserName();
        this.title =post.getTitle();
        this.content =post.getContent();
    }
}
