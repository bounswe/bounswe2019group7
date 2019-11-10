package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.resource.ArticleResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/article")
@Api(value = "Article", tags = {"Operations related with articles"})
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    @ApiOperation(value = "Get all articles ordered by time", response = ArticleResource.class
    ,responseContainer = "List")
    @GetMapping("/get_articles")
    public List<ArticleResource> getArticles(){
        return articleService.getArticles();
    }

    @ApiOperation(value = "Get all articles of a user", response = ArticleResource.class,
    responseContainer = "List")
    @GetMapping("/get_user_articles")
    public List<ArticleResource> getUserArticles(
            @RequestParam String userEmail
    ){
        return articleService.getArticles(userEmail);
    }

    @ApiOperation(value = "Get current users articles", response = ArticleResource.class,
    responseContainer = "List")
    @GetMapping("/get_self_articles")
    public List<ArticleResource> getSelfArticles(
            @RequestHeader("Authorization") String token
    ) throws IllegalAccessException {
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        return articleService.getArticles(userID);
    }

    @ApiOperation(value = "Get article by id", response = ArticleResource.class)
    @GetMapping("/get_article")
    public ArticleResource getArticle(@RequestParam UUID id){
        return articleService.getArticle(id);
    }

    @ApiOperation(value = "Create an article", response = ArticleResource.class)
    @PostMapping("/create_article")
    public ArticleResource createArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ArticleDto articleDto
    ) throws IllegalAccessException{
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        ArticleResource article = articleService.createArticle(userID,articleDto);
        return article;
    }

    @ApiOperation(value = "Update an article", response = ArticleResource.class)
    @PostMapping("/update_article")
    public ArticleResource updateArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ArticleDto articleDto,
            @RequestParam UUID articleID
    )throws IllegalAccessException{
        UUID userID = jwtUserChecker.resolveBasicToken(token);
        ArticleResource article = articleService.updateArticle(userID,articleDto,articleID);
        return article;

    }


}
