package com.eyetrade.backend.repository;

import com.eyetrade.backend.constants.CommentType;
import com.eyetrade.backend.model.entity.Annotation;
import com.eyetrade.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, UUID> {

    Annotation findAnnotationById(UUID id);
    List<Annotation> findAnnotationsByArticleEventIdAndCommentType(UUID articleEventId, CommentType commentType);
    List<Annotation> findAnnotationsByUser(User user);
}
