package com.eyetrade.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private String title;

    private String content;

    private Boolean isBelongToArticle;

    private UUID articleEventId;

}
