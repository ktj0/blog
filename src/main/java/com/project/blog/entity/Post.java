package com.project.blog.entity;

import com.project.blog.dto.PostRequestDto;
import com.project.blog.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post(PostRequestDto postRequestPost, String username) {
        this.username = username;
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
    }

    public void update(PostRequestDto postRequestPost, String username) {
        this.username = username;
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
    }
}
