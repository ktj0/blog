package com.project.blog.service;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.dto.PostResponseDto;
import com.project.blog.entity.Post;
import com.project.blog.entity.PostLike;
import com.project.blog.entity.User;
import com.project.blog.repository.PostLikeRepository;
import com.project.blog.repository.PostRepository;
import com.project.blog.security.UserDetailsImpl;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    // post 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      UserDetailsImpl userDetails) {
        Post post = new Post(postRequestDto, userDetails);

        Post savePost = postRepository.save(post);

        return new PostResponseDto(savePost);
    }

    // post 전체 조회
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    // post 조회
    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);

        return new PostResponseDto(post);
    }

    // post 수정
    @Transactional
    public PostResponseDto updatePost(Long id,
                                      PostRequestDto postRequestDto,
                                      UserDetailsImpl userDetails) {
        Post post = findPost(id);

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("수정 권한이 존재하지 않습니다.");
        }

        post.update(postRequestDto);

        return new PostResponseDto(post);
    }

    // post 삭제
    public void deletePost(Long id,
                           UserDetailsImpl userDetails) {
        Post post = findPost(id);

        if (!post.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new IllegalArgumentException("삭제 권한이 존재하지 않습니다.");
        }

        postRepository.delete(post);
    }

    // 게시글 좋아요
    public void likePost(Long id, UserDetailsImpl userDetails) {
        Post post = findPost(id);
        User user = userDetails.getUser();

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new DuplicateRequestException("이미 좋아요 한 게시글입니다.");
        } else {
            PostLike postLike = new PostLike(user, post);

            postLikeRepository.save(postLike);
        }
    }

    // 게시글 좋아요 취소
    public void deleteLikePost(Long id, UserDetailsImpl userDetails) {
        Post post = findPost(id);
        User user = userDetails.getUser();

        Optional<PostLike> postLike = postLikeRepository.findByUserAndPost(user, post);

        if (postLike.isPresent()) {
            postLikeRepository.delete(postLike.get());
        } else {
            throw new IllegalArgumentException("해당 게시글에 취소할 좋아요가 없습니다.");
        }
    }

    // post 존재여부 확인
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("해당 게시글이 존재하지 않습니다.")
        );
    }
}
