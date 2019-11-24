package com.eyetrade.backend.service;

import com.eyetrade.backend.mapper.EventMapper;
import com.eyetrade.backend.model.entity.Event;
import com.eyetrade.backend.model.resource.event.EventsResource;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.repository.EventRepository;
import com.eyetrade.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.eyetrade.backend.constants.ErrorConstants.POINT_SHOULD_BE_INSIDE_RANGE;
import static com.eyetrade.backend.constants.EventConstants.RESOURCE_TIME_FORMAT;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventsResource getEvents() {
        List<EventResource> instances = new ArrayList<>();
        List<Event> events = eventRepository.findAllByOrderByAdditionDateDesc();
        for (Event event : events) {
            EventResource resource = EventMapper.entityToResource(event);
            instances.add(resource);
        }
        EventsResource resource = new EventsResource();
        resource.setInstances(instances);
        return resource;
    }

    public EventResource getEvent(UUID guid) {
        Event event = eventRepository.findEventByGuid(guid);
        return EventMapper.entityToResource(event);
    }

    @Transactional
    public EventResource givePoint(UUID guid, Double score) {
        if (score < 0 || score > 5) {
            throw new IllegalArgumentException(POINT_SHOULD_BE_INSIDE_RANGE);
        }
        Event event = eventRepository.getOne(guid);
        int givenScoreCount = event.getGivenScoreCount();
        event.setScore((event.getScore() * givenScoreCount + score) / (givenScoreCount + 1));
        event.setGivenScoreCount(++givenScoreCount);
        eventRepository.save(event);
        return EventMapper.entityToResource(event);
    }

}
