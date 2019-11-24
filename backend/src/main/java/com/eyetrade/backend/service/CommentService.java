package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.CommentMapper;
import com.eyetrade.backend.model.dto.CommentDto;
import com.eyetrade.backend.model.entity.Comment;
import com.eyetrade.backend.model.resource.comment.CommentResource;
import com.eyetrade.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.ZonedDateTime;
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
    public CommentResource getComment(UUID commentId){
           return mapper.entityToResource(repository.findCommentById(commentId));
    }

    public List<CommentResource> getCommentsOfArticleOrEvent(UUID articleOrCommandId){
            return mapper.entityToResource(repository.findCommentsByArticleOrEventId(articleOrCommandId));
    }

    public CommentResource postComment(CommentDto dto){
        Comment comment=mapper.dtoToEntity(dto);
        comment.setCreatedDate(dateTimeFormatter(new Date(),DB_DATE_TIME_FORMAT));
        return mapper.entityToResource(repository.save(comment));
    }

}
