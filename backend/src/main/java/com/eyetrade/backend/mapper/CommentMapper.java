package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.CommentDto;
import com.eyetrade.backend.model.entity.Comment;
import com.eyetrade.backend.model.resource.comment.CommentResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public interface CommentMapper extends Converter<CommentDto , Comment, CommentResource> {

    List<CommentResource> entityToResource(List<Comment> comments);

    @Mapping(target="minimalUserResource", source="user")
    CommentResource entityToResource(Comment comment);
}
