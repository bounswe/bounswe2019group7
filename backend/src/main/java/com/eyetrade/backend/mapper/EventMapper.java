package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.entity.Event;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.utils.DateUtils;

import static com.eyetrade.backend.constants.EventConstants.RESOURCE_TIME_FORMAT;

public class EventMapper {

    public static EventResource entityToResource(Event event){
        return new EventResource(event.getGuid(),
                event.getTitle(),
                event.getContent(),
                DateUtils.dateTimeFormatter(event.getAdditionDate(), RESOURCE_TIME_FORMAT),
                event.getLink(),
                event.getScore());
    }

}
