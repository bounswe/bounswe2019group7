package com.eyetrade.backend.model.resource.annotation;

import com.eyetrade.backend.constants.CommentType;
import com.eyetrade.backend.model.entity.User;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 8 Ara 2019
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Resource
public class AnnotationResource {
    private UUID id;

    private String content;

    private MinimalUserResource user;

    //article or event
    private CommentType commentType;

    private UUID articleEventId;

    private Integer firstChar;

    private Integer lastChar;
}
