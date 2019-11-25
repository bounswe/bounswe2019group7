package com.eyetrade.backend.model.resource.comment;

import com.eyetrade.backend.constants.CommentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Resource
public class CommentResource {

    private UUID id;

    private String title;

    private String content;

    private String createdDate;

    private CommentType commentType;

    private UUID articleEventId;

}
