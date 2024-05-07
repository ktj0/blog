package com.project.blog.controller;

import com.project.blog.dto.*;
import com.project.blog.entity.UserRoleEnum;
import com.project.blog.service.AdminService;
import com.project.blog.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    // 게시글 수정
    @Secured(UserRoleEnum.Authority.ADMIN)
    @PutMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> updatePostByAdmin(@PathVariable Long id,
                                             @RequestBody PostRequestDto requestDto) {
        try {
            PostResponseDto result = adminService.updatePostByAdmin(id, requestDto);

            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시글 삭제
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> deletePostByAdmin(@PathVariable Long id) {
        try {
            adminService.deletePostByAdmin(id);

            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 댓글 수정
    @Secured(UserRoleEnum.Authority.ADMIN)
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> updateCommentByAdmin(@PathVariable Long commentId,
                                                               @RequestBody CommentRequestDto requestDto) {
        try {
            CommentResponseDto result = adminService.updateCommentByAdmin(commentId, requestDto);

            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 댓글 삭제
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteCommentByAdmin(@PathVariable Long commentId) {
        try {
            adminService.deleteCommentByAdmin(commentId);

            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // admin이 아닌 사용자 접근 차단
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.badRequest().body(new ApiResponseDto("권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
    }
}
