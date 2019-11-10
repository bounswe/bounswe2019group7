package com.eyetrade.backend.model.resource.event;

import com.eyetrade.backend.model.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */

@Resource
@Getter
@Setter
@NoArgsConstructor
public class EventResource {
    private List<EventResourceInstance> instances;
}
