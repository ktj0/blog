package com.project.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikedResponseDto extends ApiResponseDto {
    private boolean isLiked;

    public LikedResponseDto(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
