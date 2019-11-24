package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.ErrorConstants;
import com.eyetrade.backend.mapper.ArticleMapper;
import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.article.ArticlesResource;
import com.eyetrade.backend.repository.ArticleRepository;
import com.eyetrade.backend.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public ArticlesResource getArticles(){
        List<ArticleResource> resources = new ArrayList<>();
        List<Article> articles = articleRepository.findAllByOrderByAdditionDateDesc();

        for(Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return  new ArticlesResource(resources);
    }

    public ArticlesResource getTopArticles(){
        List<ArticleResource> resources = new ArrayList<>();
        List<Article> articles = articleRepository.findAllByOrderByScoreDesc();

        for(Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return  new ArticlesResource(resources);
    }

    public ArticlesResource getSelfArticles(UUID userId){
        User user = userRepository.findById(userId);
        return getArticles(user);
    }

    public ArticlesResource getSelfTopArticles(UUID userId){
        User user = userRepository.findById(userId);
        return getTopArticles(user);
    }

    public ArticlesResource getOtherUserArticles(String email){
        User user = userRepository.findByEmail(email);
        return getArticles(user);
    }

    public ArticlesResource getOtherUserTopArticles(String email){
        User user = userRepository.findByEmail(email);
        return getTopArticles(user);
    }


    private ArticlesResource getArticles(User user){
        List<ArticleResource> resources = new ArrayList<>();
        List<Article> articles = articleRepository.findAllByAuthorEmailOrderByAdditionDateDesc(user.getEmail());

        for (Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return new ArticlesResource(resources);
    }

    private ArticlesResource getTopArticles(User user){
        List<ArticleResource> resources = new ArrayList<>();
        List<Article> articles = articleRepository.findAllByAuthorEmailOrderByScoreDesc(user.getEmail());

        for (Article article: articles){
            resources.add(ArticleMapper.entityToArticleResource(article));
        }
        return new ArticlesResource(resources);
    }


    public ArticleResource getArticle(UUID id){
        Article article = articleRepository.findArticleById(id);
        return ArticleMapper.entityToArticleResource(article);
    }

    public ArticleResource createArticle(UUID userId, ArticleDto articleDto){
        User user = userRepository.findById(userId);
        Article article = ArticleMapper.articleDtoToEntity(articleDto, user);
        article.setAdditionDate(Calendar.getInstance().getTime());
        article.setLastChangeDate(Calendar.getInstance().getTime());
        articleRepository.save(article);
        return ArticleMapper.entityToArticleResource(article);
    }

    public ArticleResource updateArticle(UUID userId, ArticleDto articleDto, UUID articleID){
        User user = userRepository.findById(userId);
        Article article = articleRepository.getOne(articleID);
        if(!article.getAuthorEmail().equals(user.getEmail())){
            throw new RuntimeException(ErrorConstants.NOT_AUTHORIZED_FOR_OPERATION);
        }
        else{
            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            article.setContentAbstract(articleDto.getContentAbstract());
            article.setLastChangeDate(Calendar.getInstance().getTime());
            articleRepository.save(article);
            return ArticleMapper.entityToArticleResource(article);
        }
    }

    public ArticleResource givePoint(Double score, UUID articleID){
        if(score < 0 || score > 5){
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
