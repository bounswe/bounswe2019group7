package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.article.ArticlesResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/article")
@Api(value = "Article", tags = {"Operations related with articles"})
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Get all articles ordered by time", response = ArticlesResource.class)
    @GetMapping("/articles")
    public ArticlesResource getArticles(){
        return articleService.getArticles();
    }

    @ApiOperation(value = "Get top articles", response = ArticlesResource.class)
    @GetMapping("/top_articles")
    public ArticlesResource getTopArticles(){
        return articleService.getTopArticles();
    }


    @ApiOperation(value = "Get all articles of a user ordered by time", response = ArticlesResource.class)
    @GetMapping("/user_articles")
    public ArticlesResource getUserArticles(
            @RequestParam String userEmail
    ){
        return articleService.getOtherUserArticles(userEmail);
    }

    @ApiOperation(value = "Get top articles of a user", response = ArticlesResource.class)
    @GetMapping("/user_top_articles")
    public ArticlesResource getUserTopArticles(
            @RequestParam String userEmail
    ){
        return articleService.getOtherUserTopArticles(userEmail);
    }

    @ApiOperation(value = "Get current users articles ordered by time", response = ArticlesResource.class)
    @GetMapping("/self_articles")
    public ArticlesResource getSelfArticles(
            @RequestHeader("Authorization") String token
    ) throws IllegalAccessException {
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        return articleService.getSelfArticles(userId);
    }

    @ApiOperation(value = "Get current users top articles", response = ArticlesResource.class)
    @GetMapping("/self_top_articles")
    public ArticlesResource getSelfTopArticles(
            @RequestHeader("Authorization") String token
    )throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        return articleService.getSelfTopArticles(userId);
    }

    @ApiOperation(value = "Get article by id", response = ArticleResource.class)
    @GetMapping("/article")
    public ArticleResource getArticle(@RequestParam UUID id){
        return articleService.getArticle(id);
    }

    @ApiOperation(value = "Create an article", response = ArticleResource.class)
    @PostMapping("/create")
    public ArticleResource createArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ArticleDto articleDto
    ) throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        ArticleResource article = articleService.createArticle(userId, articleDto);
        return article;
    }

    @ApiOperation(value = "Update an article", response = ArticleResource.class)
    @PostMapping("/update")
    public ArticleResource updateArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ArticleDto articleDto,
            @RequestParam UUID articleID
    )throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        ArticleResource article = articleService.updateArticle(userId, articleDto, articleID);
        return article;
    }

    @ApiOperation(value = "Give a point to an article", response = ArticleResource.class)
    @PostMapping("/give_point")
    public ArticleResource givePoint(
            @RequestHeader("Authorization") String token,
            @RequestParam Double score,
            @RequestParam UUID articleID
    )throws IllegalAccessException{
        UUID userId = jwtUserChecker.resolveBasicToken(token);
        ArticleResource article = articleService.givePoint(score, articleID);
        return article;
    }

}
