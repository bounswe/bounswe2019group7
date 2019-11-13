package com.eyetrade.backend.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticlesResource {

    private List<ArticleResource> articles;
}
