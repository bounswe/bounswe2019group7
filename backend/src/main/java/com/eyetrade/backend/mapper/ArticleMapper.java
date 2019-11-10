package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.resource.ArticleResource;

public class ArticleMapper {

    public static Article articleDtoToEntity(ArticleDto articleDto){
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        return article;
    }

    public static ArticleResource entityToArticleResource(Article article){
        ArticleResource articleResource = new ArticleResource();
        articleResource.setAuthId(article.getAuthorId());
        articleResource.setTitle(article.getTitle());
        articleResource.setContent(article.getContent());
        articleResource.setStringDate(article.getAdditionDate().toString());
        articleResource.setScore(article.getScore());
        articleResource.setUuid(article.getId());
        articleResource.setChangeDate(article.getLastChangeDate().toString());
        articleResource.setAuthorEmail(article.getAuthorEmail());
        return articleResource;
    }
}
