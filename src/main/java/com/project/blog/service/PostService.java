package com.project.blog.service;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.dto.PostResponseDto;
import com.project.blog.entity.Post;
import com.project.blog.entity.User;
import com.project.blog.repository.PostRepository;
import com.project.blog.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // post 생성
    public PostResponseDto createPost(PostRequestDto postRequestDto,
                                      String username) {
        Post post = new Post(postRequestDto, username);

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
                                      User user) {
        Post post = findPost(id);
        String username = user.getUsername();

        if (!post.getUsername().equals(username)) {
            throw new IllegalArgumentException("수정 권한이 존재하지 않습니다.");
        }

        post.update(postRequestDto, username);

        return new PostResponseDto(post);
    }

    // post 삭제
    public void deletePost(Long id,
                           User user) {
        Post post = findPost(id);

        if (!post.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("삭제 권한이 존재하지 않습니다.");
        }

        postRepository.delete(post);
    }

    // post 존재여부 확인
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("해당 게시글이 존재하지 않습니다.")
        );
    }
}
