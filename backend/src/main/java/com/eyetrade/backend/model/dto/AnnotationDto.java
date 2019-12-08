package com.eyetrade.backend.model.dto;

import com.eyetrade.backend.constants.CommentType;
import com.eyetrade.backend.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 8 Ara 2019
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDto {

    private String content;

    //article or event
    private CommentType commentType;

    private UUID articleEventId;

    private Integer firstChar;

    private Integer lastChar;
}
