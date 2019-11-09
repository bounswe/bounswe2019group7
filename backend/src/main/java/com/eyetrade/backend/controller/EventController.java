package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.entity.EventRssFeed;
import com.eyetrade.backend.model.resource.event.EventResource;
import com.eyetrade.backend.model.resource.event.EventResourceInstance;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.EventRssReaderService;
import com.eyetrade.backend.service.EventService;
import com.sun.syndication.io.FeedException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 8 Kas 2019
 */
@RestController
@RequestMapping("/event")
@Api(value = "Event", tags = {"Operations Related With Event"})
public class EventController {

    @Autowired
    private EventRssReaderService rssReaderService;

    @Autowired
    private EventService eventService;

    @Autowired
    private JwtUserChecker jwtUserChecker;

    // TODO: 9 Kas 2019 It will be removed when it's stable
    @ApiOperation(value = "Update events bu using a rss source", response = EventRssFeed.class)
    @GetMapping("/update_events")
    public EventRssFeed updateEvents() throws FeedException {
        return rssReaderService.readAndSaveFeed();
    }

    @ApiOperation(value = "Rss element description", response = EventResourceInstance.class)
    @GetMapping("/get_an_event")
    public EventResourceInstance getEvent(@RequestParam UUID id) {
        return eventService.getEvent(id);
    }

    @ApiOperation(value = "Get events order by time", response = List.class)
    @GetMapping("/get_events")
    public EventResource getEvents() {
        return eventService.getEvents();
    }

    @ApiOperation(value = "Give point to an event over 5", response = EventResourceInstance.class)
    @PostMapping("/give_point")
    public EventResourceInstance givePoint(@RequestHeader("Authorization") String token, @RequestParam UUID id, @RequestParam Double score) throws IllegalAccessException {
        jwtUserChecker.resolveBasicToken(token);
        return eventService.givePoint(id, score);
    }

}
