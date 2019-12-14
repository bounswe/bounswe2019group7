package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.dto.ArticleDto;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.article.ArticlesResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/article")
@Api(value = "Article", tags = {"Articles"})
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
    public ResponseEntity getSelfArticles(
            @RequestHeader("Authorization") String token
    ){
        try {
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            return ResponseEntity.ok(articleService.getSelfArticles(userId));
        }
        catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get current users top articles", response = ArticlesResource.class)
    @GetMapping("/self_top_articles")
    public ResponseEntity getSelfTopArticles(
            @RequestHeader("Authorization") String token
    ){
        try {
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            return ResponseEntity.ok(articleService.getSelfTopArticles(userId));
        }
        catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Get article by id", response = ArticleResource.class)
    @GetMapping("/article")
    public ArticleResource getArticle(@RequestParam UUID id){
        return articleService.getArticle(id);
    }

    @ApiOperation(value = "Create an article", response = ArticleResource.class)
    @PostMapping("/create")
    public ResponseEntity createArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ArticleDto articleDto
    ) {
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            ArticleResource article = articleService.createArticle(userId, articleDto);
            return ResponseEntity.ok(article);
        }
        catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Update an article", response = ArticleResource.class)
    @PostMapping("/update")
    public ResponseEntity updateArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ArticleDto articleDto,
            @RequestParam UUID articleID
    ){
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            ArticleResource article = articleService.updateArticle(userId, articleDto, articleID);
            return ResponseEntity.ok(article);
        }
        catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Delete an article", response = ArticleResource.class)
    @DeleteMapping("/delete")
    public ResponseEntity deleteArticle(
            @RequestHeader("Authorization") String token,
            @RequestParam UUID articleID
    ){
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            ArticleResource article = articleService.deleteArticle(userId, articleID);
            return ResponseEntity.ok(article);
        }
        catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ApiOperation(value = "Give a point to an article", response = ArticleResource.class)
    @PostMapping("/give_point")
    public ResponseEntity givePoint(
            @RequestHeader("Authorization") String token,
            @RequestParam Double score,
            @RequestParam UUID articleID
    ){
        try{
            UUID userId = jwtUserChecker.resolveBasicToken(token);
            ArticleResource article = articleService.givePoint(score, articleID,userId);
            return ResponseEntity.ok(article);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
