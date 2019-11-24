package com.eyetrade.backend.model.resource.mainpage;

import com.eyetrade.backend.model.resource.article.ArticleResource;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.model.resource.user.MinimalUserResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.annotation.Resource;
import java.util.List;

@Resource
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainPageResource {

    private List<MinimalUserResource> suggestedUsers;

    private List<ArticleResource> suggestedArticles;

    private List<EventResource> suggestedEvents;

}
