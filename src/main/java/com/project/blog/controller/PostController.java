package com.project.blog.controller;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.dto.PostResponseDto;
import com.project.blog.security.UserDetailsImpl;
import com.project.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
    public PostResponseDto updatePost(@PathVariable Long id,
                                      @RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails);
    }

    // post 삭제
    @DeleteMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails);

        return ResponseEntity.ok(new PostResponseDto(true));
    }
}
