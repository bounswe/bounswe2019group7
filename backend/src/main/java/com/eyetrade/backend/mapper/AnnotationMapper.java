package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.AnnotationDto;
import com.eyetrade.backend.model.entity.Annotation;
import com.eyetrade.backend.model.resource.annotation.AnnotationResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 8 Ara 2019
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE,uses = UserMapper.class)
public interface AnnotationMapper extends Converter<AnnotationDto, Annotation, AnnotationResource> {

    List<AnnotationResource> entityToResource(List<Annotation> annotations);
}
