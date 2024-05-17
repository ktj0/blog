package com.project.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.blog.entity.Post;
import com.project.blog.entity.PostLike;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
public class PostResponseDto extends ApiResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final String username;
    private final List<CommentResponseDto> comments;
    private final Integer likeCount;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.username = post.getUser().getUsername();
        this.likeCount = post.getPostLikes().size();
        this.comments = post.getComments().stream()
                .map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreatedAt).reversed())
                .toList();
    }
}
