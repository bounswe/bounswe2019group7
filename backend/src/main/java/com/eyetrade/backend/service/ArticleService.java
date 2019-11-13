package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.mapper.ArticleMapper;
import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.resource.ArticleResource;
import com.eyetrade.backend.model.resource.ArticlesResource;
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

    public ArticlesResource getArticles(){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByOrderByAdditionDateDesc();

        for(Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return  new ArticlesResource(resources);
    }

    public ArticlesResource getArticles(String userEmail){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByAuthorEmailOrderByAdditionDateDesc(userEmail);

        for (Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return new ArticlesResource(resources);
    }


    public ArticleResource getArticle(UUID id){
        Article article = articleRepository.findArticleById(id);
        return ArticleMapper.entityToArticleResource(article);
    }

    public ArticleResource createArticle(ArticleDto articleDto){
        Article article = ArticleMapper.articleDtoToEntity(articleDto);
        article.setAdditionDate(Calendar.getInstance().getTime());
        article.setLastChangeDate(Calendar.getInstance().getTime());
        articleRepository.save(article);
        return ArticleMapper.entityToArticleResource(article);
    }

    public ArticleResource updateArticle(ArticleDto articleDto, UUID articleID){
        Article article=articleRepository.getOne(articleID);
        if(article.getAuthorEmail()!=articleDto.getAuthorEmail()){
            throw new RuntimeException(ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION);
        }else{
            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            article.setLastChangeDate(Calendar.getInstance().getTime());
            articleRepository.save(article);
            return ArticleMapper.entityToArticleResource(article);
        }
    }

    public ArticleResource givePoint(Double score, UUID articleID){
        if(score<0 || score > 5){
            throw new IllegalArgumentException(ErrorConstants.POINT_SHOULD_BE_INSIDE_RANGE);
        }
        Article article = articleRepository.getOne(articleID);
        int scoreCount = article.getGivenScoreCount();
        article.setScore((article.getScore() * scoreCount + score) / (scoreCount + 1));
        article.setGivenScoreCount(++scoreCount);
        articleRepository.save(article);
        return ArticleMapper.entityToArticleResource(article);

    }
}
