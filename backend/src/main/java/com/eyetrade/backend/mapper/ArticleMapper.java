package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;

public class ArticleMapper {

    public static Article articleDtoToEntity(ArticleDto articleDto, User user){
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setContentAbstract(articleDto.getContentAbstract());
        article.setAuthorEmail(user.getEmail());
        article.setAuthorName(user.getName());
        article.setAuthorSurname(user.getSurname());
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
