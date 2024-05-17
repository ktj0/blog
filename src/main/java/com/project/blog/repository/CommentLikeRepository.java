package com.project.blog.repository;

import com.project.blog.entity.Comment;
import com.project.blog.entity.CommentLike;
import com.project.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
