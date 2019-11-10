package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.mapper.ArticleMapper;
import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.resource.ArticleResource;
import com.eyetrade.backend.repository.ArticleRepository;
import com.eyetrade.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.eyetrade.backend.constants.ArticleConstants.RESOURCE_TIME_FORMAT;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleResource> getArticles(){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByOrderByAdditionDateDesc();

        for(Article article: articles){
            ArticleResource articleResource = new ArticleResource(article.getId(), article.getTitle(),
                    article.getContent(),DateUtils.TimeFormatter(article.getAdditionDate(),RESOURCE_TIME_FORMAT),
                    DateUtils.TimeFormatter(article.getLastChangeDate(),RESOURCE_TIME_FORMAT),
                    article.getAuthorId(),article.getScore(),article.getAuthorEmail());
            resources.add(articleResource);
        }
        return  resources;
    }

    public List<ArticleResource> getArticles(UUID userID){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByAuthorIdOrderByAdditionDateDesc(userID);

        for(Article article: articles){
            ArticleResource articleResource = new ArticleResource(article.getId(), article.getTitle(),
                    article.getContent(), DateUtils.TimeFormatter(article.getAdditionDate(), RESOURCE_TIME_FORMAT),
                    DateUtils.TimeFormatter(article.getLastChangeDate(),RESOURCE_TIME_FORMAT),
                    article.getAuthorId(), article.getScore(),article.getAuthorEmail());
            resources.add(articleResource);
        }
        return  resources;
    }

    public List<ArticleResource> getArticles(String userEmail){
        List<ArticleResource> resources = new ArrayList<ArticleResource>();
        List<Article> articles = articleRepository.findAllByAuthorEmailOrderByAdditionDateDesc(userEmail);

        for (Article article: articles){
            ArticleResource articleResource = new ArticleResource(article.getId(), article.getTitle(),
                    article.getContent(), DateUtils.TimeFormatter(article.getAdditionDate(), RESOURCE_TIME_FORMAT),
                    DateUtils.TimeFormatter(article.getLastChangeDate(),RESOURCE_TIME_FORMAT),
                    article.getAuthorId(), article.getScore(),article.getAuthorEmail());
            resources.add(articleResource);
        }
        return resources;
    }


    public ArticleResource getArticle(UUID id){
        Article article = articleRepository.findArticleById(id);
        return new ArticleResource(article.getId(), article.getTitle(),
                article.getContent(), DateUtils.TimeFormatter(article.getAdditionDate(), RESOURCE_TIME_FORMAT),
                DateUtils.TimeFormatter(article.getLastChangeDate(),RESOURCE_TIME_FORMAT),
                article.getAuthorId(), article.getScore(),article.getAuthorEmail());
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
