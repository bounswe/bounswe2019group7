package com.eyetrade.backend.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import javax.persistence.Column;
import java.util.UUID;

@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResource {
    private UUID uuid;
    private String title;
    @Column(length = 1024)
    private String content;
    private String stringDate;
    private String changeDate;
    private Double score;
    private String authorName;
    private String authorSurname;
    private String authorEmail;
}
