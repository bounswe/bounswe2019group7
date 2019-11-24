package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.CommentDto;
import com.eyetrade.backend.model.entity.Comment;
import com.eyetrade.backend.model.resource.comment.CommentResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CommentMapper extends Converter<CommentDto , Comment, CommentResource> {
}
