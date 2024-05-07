package com.project.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private final Boolean result;
    private final Long id;
    private final String username;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final List<CommentResponseDto> comments;

    public PostResponseDto(Post post) {
        this.result = null;
        this.id = post.getId();
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.comments = post.getComments().stream()
                .map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed())
                .toList();
    }

    public PostResponseDto(Boolean result) {
        this.result = result;
        this.id = null;
        this.username = null;
        this.title = null;
        this.content = null;
        this.createdAt = null;
        this.comments = null;
    }
}
