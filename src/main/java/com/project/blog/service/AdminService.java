package com.project.blog.service;

import com.project.blog.dto.CommentRequestDto;
import com.project.blog.dto.CommentResponseDto;
import com.project.blog.dto.PostRequestDto;
import com.project.blog.dto.PostResponseDto;
import com.project.blog.entity.Comment;
import com.project.blog.entity.Post;
import com.project.blog.repository.CommentRepository;
import com.project.blog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePostByAdmin(Long id,
                                             PostRequestDto requestDto) {
        Post post = postService.findPost(id);

        post.update(requestDto);

        return new PostResponseDto(post);
    }

    // 게시글 삭제
    public void deletePostByAdmin(Long id) {
        Post post = postService.findPost(id);

        postRepository.delete(post);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateCommentByAdmin(Long commentId,
                                                   CommentRequestDto requestDto) {
        Comment comment = commentService.findComment(commentId);

        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = commentService.findComment(commentId);

        commentRepository.delete(comment);
    }
}
