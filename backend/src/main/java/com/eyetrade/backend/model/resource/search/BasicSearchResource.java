package com.eyetrade.backend.model.resource.search;

import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 22 Ara 2019
 */
@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicSearchResource {

    private List<EventResource> eventResources;

    private List<ArticleResource> articleResources;

    private List<MinimalUserResource> minimalUserResources;
}
