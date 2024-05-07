package com.project.blog.controller;

import com.project.blog.dto.ApiResponseDto;
import com.project.blog.dto.CommentRequestDto;
import com.project.blog.dto.CommentResponseDto;
import com.project.blog.security.UserDetailsImpl;
import com.project.blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{postId}/comment")
    public ResponseEntity<ApiResponseDto> createComment(@PathVariable Long postId,
                                            @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto result = commentService.createComment(postId, requestDto, userDetails.getUser());

            return ResponseEntity.ok().body(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId,
                                                        @RequestBody CommentRequestDto requestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto result = commentService.updateComment(commentId, requestDto, userDetails.getUser());

            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId,
                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            commentService.deleteComment(commentId, userDetails.getUser());

            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("댓글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
