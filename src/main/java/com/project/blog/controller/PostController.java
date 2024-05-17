package com.project.blog.controller;

import com.project.blog.dto.*;
import com.project.blog.security.UserDetailsImpl;
import com.project.blog.service.PostService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    // post 생성
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.createPost(postRequestDto, userDetails);
    }

    // post 전체 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    // post 조회
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // post 수정
    @PutMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            PostResponseDto result = postService.updatePost(id, postRequestDto, userDetails);

            return ResponseEntity.ok().body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("수정 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // post 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<ApiResponseDto> deletePost(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deletePost(id, userDetails);

            return ResponseEntity.ok().body(new ApiResponseDto("삭제 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("삭제 권한이 없습니다.", HttpStatus.BAD_REQUEST.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시글 좋아요
    @PostMapping("/post/{id}/like")
    public ResponseEntity<ApiResponseDto> likePost(@PathVariable Long id,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.likePost(id, userDetails);

            return ResponseEntity.ok().body(new ApiResponseDto("좋아요", HttpStatus.OK.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        } catch (DuplicateRequestException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("이미 좋아요 한 게시글입니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/post/{id}/like")
    public ResponseEntity<ApiResponseDto> deleteLikePost(@PathVariable Long id,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.deleteLikePost(id, userDetails);

            return ResponseEntity.ok().body(new ApiResponseDto("좋아요 취소", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("해당 게시글에 취소할 좋아요가 없습니다.", HttpStatus.BAD_REQUEST.value()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
