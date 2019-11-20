package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.resource.article.ArticleResource;

public class ArticleMapper {

    public static Article articleDtoToEntity(ArticleDto articleDto){
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setContentAbstract(articleDto.getContentAbstract());
        article.setAuthorEmail(articleDto.getAuthorEmail());
        article.setAuthorName(articleDto.getAuthorName());
        article.setAuthorSurname(articleDto.getAuthorSurname());
        return article;
    }

    public static ArticleResource entityToArticleResource(Article article){
        ArticleResource articleResource = new ArticleResource();
        articleResource.setTitle(article.getTitle());
        articleResource.setContent(article.getContent());
        articleResource.setContentAbstract(article.getContentAbstract());
        articleResource.setStringDate(article.getAdditionDate().toString());
        articleResource.setScore(article.getScore());
        articleResource.setUuid(article.getId());
        articleResource.setChangeDate(article.getLastChangeDate().toString());
        articleResource.setAuthorName(article.getAuthorName());
        articleResource.setAuthorSurname(article.getAuthorSurname());
        articleResource.setAuthorEmail(article.getAuthorEmail());
        return articleResource;
    }
}
