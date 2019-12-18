package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.service.SearchService;
import com.sipios.springsearch.anotation.SearchSpec;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            "/search/user?search=name:'Emir' AND city:'Erzurum' \n" +
            "if there is no parameter then result will return all users", response = MinimalUserResource.class, responseContainer = "List")
    @GetMapping("/user")
    public ResponseEntity searchForUsers(@SearchSpec Specification<User> specs) {
        try {
            return ResponseEntity.ok(service.searchUser(specs)) ;
        }catch (InvalidDataAccessApiUsageException exception){
            return ResponseEntity.badRequest().body(SEARCH_TEXT_IS_WRONG);
        }
    }

}
