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
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Post(PostRequestDto postRequestPost, UserDetailsImpl userDetails) {
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
        this.user = userDetails.getUser();
    }

    public void update(PostRequestDto postRequestPost) {
        this.title = postRequestPost.getTitle();
        this.content = postRequestPost.getContent();
    }
}
