package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    Article findArticleById(UUID id);

    Article findArticleByTitle(String title);

    List<Article> findAllByOrderByAdditionDateDesc();

    List<Article> findAllByAuthorIdOrderByAdditionDateDesc(UUID id);

    List<Article> findAllByAuthorEmailOrderByAdditionDateDesc(String authorEmail);
}
