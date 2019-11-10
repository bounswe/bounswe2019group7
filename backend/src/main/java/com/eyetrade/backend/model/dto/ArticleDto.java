package com.eyetrade.backend.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleDto {
    public String authorEmail;
    public String authorName;
    public String authorSurname;
    public String title;
    public String content;
}
