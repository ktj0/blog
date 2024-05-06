package com.project.blog.entity;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;

    public Post(PostRequestDto postRequestPost, UserDetailsImpl userDetails) {
        this.username = userDetails.getUsername();
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
    }

    public void update(PostRequestDto postRequestPost, UserDetailsImpl userDetails) {
        this.username = userDetails.getUsername();
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
    }
}
