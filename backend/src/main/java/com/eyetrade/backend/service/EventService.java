package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.Event;
import com.eyetrade.backend.model.resource.EventResource;
import com.eyetrade.backend.repository.EventRepository;
import com.eyetrade.backend.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.eyetrade.backend.constants.EventConstants.RESOURCE_TIME_FORMAT;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<EventResource> getEvents() {
        List<EventResource> resources = new ArrayList<>();
        List<Event> events = eventRepository.findAllByOrderByAdditionDateDesc();
        for (Event event : events) {
            EventResource resource = new EventResource(event.getGuid(),event.getTitle(), event.getContent(),
                    DateUtils.TimeFormatter(event.getAdditionDate(), RESOURCE_TIME_FORMAT),
                    event.getScore());
            resources.add(resource);
        }
        return resources;
    }

    public EventResource getEvent(UUID guid) {
        Event event=eventRepository.findEventByGuid(guid);
        return new EventResource(event.getGuid(),event.getTitle(),event.getContent(),
                DateUtils.TimeFormatter(event.getAdditionDate(),RESOURCE_TIME_FORMAT),
                event.getScore());
    }

    @Transactional
    public EventResource givePoint(UUID guid, Double score){
        Event event=eventRepository.getOne(guid);
        int givenScoreCount=event.getGivenScoreCount();
        event.setScore((event.getScore()*givenScoreCount+score)/(givenScoreCount+1));
        event.setGivenScoreCount(++givenScoreCount);
        eventRepository.save(event);
        return new EventResource(event.getGuid(),event.getTitle(),event.getContent(),
                DateUtils.TimeFormatter(event.getAdditionDate(),RESOURCE_TIME_FORMAT),
                event.getScore());
    }
}
