package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.repository.ArticleRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.eyetrade.backend.mapper.ArticleMapper.entityToArticleResource;
import static com.eyetrade.backend.mapper.UserMapper.entityToMinimalUserResource;

/**
 * Created by Emir Gökdemir
 * on 19 Ara 2019
 */
@Service
public class SearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<MinimalUserResource> searchUser(Specification<User> specs){
        List<MinimalUserResource> userResources=new ArrayList<>();
        for (User user:userRepository.findAll(Specification.where(specs))){
            userResources.add(entityToMinimalUserResource(user));
        }
        return userResources;
    }

}
