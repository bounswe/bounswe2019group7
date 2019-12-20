package com.eyetrade.backend.repository;

import com.eyetrade.backend.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, UUID>, JpaSpecificationExecutor<Article> {

    Article findArticleById(UUID id);

    Article findArticleByTitle(String title);

    List<Article> findAllByOrderByAdditionDateDesc();

    List<Article> findAllByOrderByScoreDesc();

    List<Article> findAllByAuthorEmailOrderByAdditionDateDesc(String authorEmail);

    List<Article> findAllByAuthorEmailOrderByScoreDesc(String authorEMail);
}
