package com.project.blog.entity;

import com.project.blog.dto.PostRequestDto;
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
    @Column(name = "userName", nullable = false)
    private String userName;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "password", nullable = false)
    private String password;

    public Post(PostRequestDto postRequestPost) {
        this.userName = postRequestPost.getUserName();
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
        this.password = postRequestPost.getPassword();
    }

    public void postUpdate(PostRequestDto postRequestPost) {
        this.userName = postRequestPost.getUserName();
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
        this.password = postRequestPost.getPassword();
    }
}
