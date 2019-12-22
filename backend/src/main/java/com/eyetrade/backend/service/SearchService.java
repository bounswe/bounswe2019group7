package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.ArticleMapper;
import com.eyetrade.backend.mapper.EventMapper;
import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.Event;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.model.resource.search.BasicSearchResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.repository.ArticleRepository;
import com.eyetrade.backend.repository.EventRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    private EventRepository eventRepository;

    public List<MinimalUserResource> searchUser(Specification<User> specs) {
        List<MinimalUserResource> userResources = new ArrayList<>();
        for (User user : userRepository.findAll(Specification.where(specs))) {
            userResources.add(entityToMinimalUserResource(user));
        }
        return userResources;
    }

    public List<ArticleResource> searchArticles(Specification<Article> specs) {
        List<ArticleResource> articleResources = new ArrayList<>();
        for (Article article : articleRepository.findAll(Specification.where(specs))) {
            articleResources.add(entityToArticleResource(article));
        }
        return articleResources;
    }

    public BasicSearchResource searchWithWord(String searchingWord) {
        searchingWord=searchingWord.toLowerCase(Locale.ENGLISH);
        List<Event> events = eventRepository.findAll();
        List<Article> articles = articleRepository.findAll();
        List<User> users = userRepository.findAll();
        List<EventResource> eventResources = new ArrayList<>();
        List<ArticleResource> articleResources = new ArrayList<>();
        List<MinimalUserResource> userResources = new ArrayList<>();
        for (User user : users) {
            if (user.toStringForSearch().contains(searchingWord)) {
                userResources.add(UserMapper.entityToMinimalUserResource(user));
            }
        }
        for (Article article : articles) {
            if (article.toStringForSearch().contains(searchingWord)) {
                articleResources.add(ArticleMapper.entityToArticleResource(article));
            }
        }
        for (Event event : events) {
            if (event.toStringForSearch().contains(searchingWord)) {
                eventResources.add(EventMapper.entityToResource(event));
            }
        }
        BasicSearchResource resource = new BasicSearchResource();
        resource.setMinimalUserResources(userResources);
        resource.setArticleResources(articleResources);
        resource.setEventResources(eventResources);
        return resource;
    }
}
