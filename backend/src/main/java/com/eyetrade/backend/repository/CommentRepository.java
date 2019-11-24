package com.eyetrade.backend.repository;


import com.eyetrade.backend.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, UUID> {
    Comment findCommentById(UUID id);
    List<Comment> findCommentsByArticleEventId(UUID articleOrEventId);
}
