package com.eyetrade.backend.model.resource.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */

@Resource
@Getter
@Setter
@NoArgsConstructor
public class EventsResource {
    private List<EventResource> instances;
}
