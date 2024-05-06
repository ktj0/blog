package com.project.blog.service;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.dto.PostResponseDto;
import com.project.blog.entity.Post;
import com.project.blog.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final PostRepository postRepository;

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePostByAdmin(Long id,
                                             PostRequestDto requestDto) {
        Post post = findPost(id);
        String username = post.getUsername();

        post.update(requestDto, username);

        return new PostResponseDto(post);
    }

    // 게시글 삭제
    public void deletePostByAdmin(Long id) {
        Post post = findPost(id);

        postRepository.delete(post);
    }

    // post 존재여부 확인
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("해당 게시글이 존재하지 않습니다.")
        );
    }
}
