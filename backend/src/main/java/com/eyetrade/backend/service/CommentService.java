package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.CommentMapper;
import com.eyetrade.backend.model.dto.CommentDto;
import com.eyetrade.backend.model.entity.Comment;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.comment.CommentResource;
import com.eyetrade.backend.repository.CommentRepository;
import com.eyetrade.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.DB_DATE_TIME_FORMAT;
import static com.eyetrade.backend.utils.DateUtils.dateTimeFormatter;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    @Autowired
    private CommentMapper mapper;

    @Autowired
    private UserRepository userRepository;

    public CommentResource getComment(UUID commentId){
           return mapper.entityToResource(repository.findCommentById(commentId));
    }

    public List<CommentResource> getCommentsOfArticleOrEvent(UUID articleOrCommandId){
            return mapper.entityToResource(repository.findCommentsByArticleEventId(articleOrCommandId));
    }

    public List<CommentResource> getCommentsOfUser(UUID userId){
        User user=userRepository.findById(userId);
        return mapper.entityToResource(repository.findCommentsByUser(user));
    }

    public CommentResource postComment(CommentDto dto,UUID userId){
        Comment comment=mapper.dtoToEntity(dto);
        comment.setCreatedDate(dateTimeFormatter(new Date(),DB_DATE_TIME_FORMAT));
        comment.setUser(userRepository.findById(userId));
        CommentResource resource=mapper.entityToResource(repository.save(comment));
        return resource;
    }

}
