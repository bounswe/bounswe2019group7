package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.search.BasicSearchResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.service.SearchService;
import com.sipios.springsearch.anotation.SearchSpec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.eyetrade.backend.constants.ErrorConstants.SEARCH_TEXT_IS_WRONG;

/**
 * Created by Emir Gökdemir
 * on 19 Ara 2019
 */

@RestController
@RequestMapping("/search")
@Api(value = "Search", tags = {"Search"})
public class SearchController {

    @Autowired
    private SearchService service;

    @ApiOperation(value = "Search users with the wanted informations. Search will be this format:\n " +
            "/search/user?search=name:'Emir' AND city:'Erzurum' \n  " +
            "if there is no parameter then result will return all users The equal operation, using the : operator\n  " +
            "The not equal operation, using the ! operator\n    " +
            "The greater than and less than operators, respectively > and <\n   " +
            "The starts with/ends with/contains operator, using *. It acts like the bash * expension.\n   " +
            "The AND operator.\n    " +
            "The OR operator.\n " +
            "Parenthesis can be used for grouping.", response = MinimalUserResource.class, responseContainer = "List")
    @GetMapping("/advanced/user")
    public ResponseEntity searchForUsers(@SearchSpec Specification<User> specs) {
        try {
            return ResponseEntity.ok(service.searchUser(specs));
        } catch (InvalidDataAccessApiUsageException exception) {
            return ResponseEntity.badRequest().body(SEARCH_TEXT_IS_WRONG);
        }
    }

    @ApiOperation(value = "Search articles with the wanted informations. Search will be this format:\n " +
            "/search/article?search=(authorName:'Emir' AND authorSurname:'*emir*') OR authorEmail:'anka@test.com' \n" +
            "if there is no parameter then result will return all articles", response = ArticleResource.class, responseContainer = "List")
    @GetMapping("/advanced/article")
    public ResponseEntity searchForArticles(@SearchSpec Specification<Article> specs) {
        try {
            return ResponseEntity.ok(service.searchArticles(specs));
        } catch (InvalidDataAccessApiUsageException exception) {
            return ResponseEntity.badRequest().body(SEARCH_TEXT_IS_WRONG);
        }
    }

    @ApiOperation(value = "Search articles,events and users with a String.", response = BasicSearchResource.class, responseContainer = "List")
    @GetMapping("/basic/{searching_word}")
    public ResponseEntity searchWithWord(@PathVariable("searching_word") String searchingWord) {
            return ResponseEntity.ok(service.searchWithWord(searchingWord));
    }

}
