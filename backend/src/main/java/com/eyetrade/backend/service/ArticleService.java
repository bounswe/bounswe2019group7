package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.mapper.ArticleMapper;
import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.resource.ArticleResource;
import com.eyetrade.backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleResource> getArticles(){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByOrderByAdditionDateDesc();

        for(Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return  resources;
    }

    public List<ArticleResource> getArticles(UUID userID){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByAuthorIdOrderByAdditionDateDesc(userID);

        for(Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return  resources;
    }

    public List<ArticleResource> getArticles(String userEmail){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByAuthorEmailOrderByAdditionDateDesc(userEmail);

        for (Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return resources;
    }


    public ArticleResource getArticle(UUID id){
        Article article = articleRepository.findArticleById(id);
        return ArticleMapper.entityToArticleResource(article);
    }

    public ArticleResource createArticle(UUID userID,ArticleDto articleDto){
        Article article = ArticleMapper.articleDtoToEntity(articleDto);
        article.setAdditionDate(Calendar.getInstance().getTime());
        article.setLastChangeDate(Calendar.getInstance().getTime());
        article.setAuthorId(userID);
        articleRepository.save(article);
        return ArticleMapper.entityToArticleResource(article);
    }

    public ArticleResource updateArticle(UUID userID, ArticleDto articleDto, UUID articleID){
        Article article=articleRepository.findArticleById(articleID);
        if(article.getAuthorId()!=userID){
            throw new RuntimeException(ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION);
        }else{
            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            article.setLastChangeDate(Calendar.getInstance().getTime());
            return ArticleMapper.entityToArticleResource(article);
        }
    }
}
