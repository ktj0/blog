package com.project.blog.controller;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.dto.PostResponseDto;
import com.project.blog.entity.UserRoleEnum;
import com.project.blog.service.AdminService;
import com.project.blog.service.PostService;
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
    public PostResponseDto updatePostByAdmin(@PathVariable Long id,
                                             @RequestBody PostRequestDto requestDto) {
        return adminService.updatePostByAdmin(id, requestDto);
    }

    // 게시글 삭제
    @Secured(UserRoleEnum.Authority.ADMIN)
    @DeleteMapping("/post/{id}")
    public ResponseEntity<PostResponseDto> deletePostByAdmin(@PathVariable Long id) {
        adminService.deletePostByAdmin(id);

        return ResponseEntity.ok(new PostResponseDto(true));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
    }
}
