package com.project.blog.dto;

import com.project.blog.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto extends ApiResponseDto {
    private final Long id;
    private final String username;
    private final String comment;
    private final LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        super();
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
    }
}
