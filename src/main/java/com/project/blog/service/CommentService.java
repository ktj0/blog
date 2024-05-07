package com.project.blog.service;

import com.project.blog.dto.CommentRequestDto;
import com.project.blog.dto.CommentResponseDto;
import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.repository.CommentRepository;
import com.project.blog.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    // 댓글 작성
    public CommentResponseDto createComment(Long postId,
                                            CommentRequestDto requestDto,
                                            User user) {
        Post post = postService.findPost(postId);

        Comment comment = new Comment(requestDto, post, user);

        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId,
                                            CommentRequestDto requestDto,
                                            User user) {
        Comment comment = findComment(commentId);

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("수정 권한이 존재하지 않습니다.");
        }

        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId,
                              User user) {
        Comment comment = findComment(commentId);

        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("삭제 권한이 존재하지 않습니다.");
        }

        commentRepository.delete(comment);
    }

    // 댓글 유무 확인
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new EntityNotFoundException("댓글이 존재하지 않습니다.")
        );
    }
}
