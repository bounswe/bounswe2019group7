package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.ArticleMapper;
import com.eyetrade.backend.mapper.EventMapper;
import com.eyetrade.backend.mapper.UserMapper;
import com.eyetrade.backend.model.entity.Article;
import com.eyetrade.backend.model.entity.Event;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.model.resource.mainpage.MainPageResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import com.eyetrade.backend.repository.ArticleRepository;
import com.eyetrade.backend.repository.EventRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MainPageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EventRepository eventRepository;

    // TODO : Iterative pagination might be added in the future
    public MainPageResource getMainPageFeed(){
        Pageable firstTen = PageRequest.of(0, 10);
        List<User> users = userRepository.findAll(firstTen).getContent();
        List<Article> articles = articleRepository.findAll(firstTen).getContent();
        List<Event> events = eventRepository.findAll(firstTen).getContent();
        List<MinimalUserResource> userResources = users
                .stream()
                .map(UserMapper::entityToMinimalUserResource)
                .collect(Collectors.toList());
        List<ArticleResource> articleResources = articles
                .stream()
                .map(ArticleMapper::entityToArticleResource)
                .collect(Collectors.toList());
        List<EventResource> eventResources = events
                .stream()
                .map(EventMapper::entityToResource)
                .collect(Collectors.toList());
        return new MainPageResource(userResources, articleResources, eventResources);
    }

}
