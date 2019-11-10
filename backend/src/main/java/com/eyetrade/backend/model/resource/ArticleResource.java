package com.eyetrade.backend.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResource {
    private UUID uuid;
    private String title;
    private String content;
    private String stringDate;
    private String changeDate;
    private UUID authId;
    private Double score;
    private String authorEmail;
}
