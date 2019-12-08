package com.eyetrade.backend.service;

import com.eyetrade.backend.constants.CommentType;
import com.eyetrade.backend.mapper.AnnotationMapper;
import com.eyetrade.backend.model.dto.AnnotationDto;
import com.eyetrade.backend.model.entity.Annotation;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.annotation.AnnotationResource;
import com.eyetrade.backend.repository.AnnotationRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 8 Ara 2019
 */
@Service
public class AnnotationService {

    @Autowired
    private AnnotationRepository repository;

    @Autowired
    private AnnotationMapper mapper;

    @Autowired
    private UserRepository userRepository;

    public AnnotationResource addAnnotation(AnnotationDto dto, UUID userId){
        User user=userRepository.findById(userId);
        Annotation annotation=mapper.dtoToEntity(dto);
        annotation.setUser(user);
        return mapper.entityToResource(repository.save(annotation));
    }

    public AnnotationResource getAnnotation(UUID annotationId){
        return mapper.entityToResource(repository.findAnnotationById(annotationId));
    }

    public List<AnnotationResource> getAnnotationsOfSelf(UUID userId){
        User user=userRepository.findById(userId);
        return mapper.entityToResource(repository.findAnnotationsByUser(user));
    }

    public List<AnnotationResource> getAnnotationsOfArticleEvent(UUID articleEventId){
        return mapper.entityToResource(repository.findAnnotationsByArticleEventId(articleEventId));
    }
}
