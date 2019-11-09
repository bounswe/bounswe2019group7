package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.entity.EventRssFeed;
import com.eyetrade.backend.model.resource.EventResource;
import com.eyetrade.backend.security.JwtUserChecker;
import com.eyetrade.backend.service.EventRssReaderService;
import com.eyetrade.backend.service.EventService;
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

    @ApiOperation(value = "Rss read elements", response = EventRssFeed.class)
    @GetMapping("/check")
    public EventRssFeed checkRss() {
        return rssReaderService.readAndSaveFeed();
    }

    @ApiOperation(value = "Rss element description", response = EventResource.class)
    @GetMapping("/get_an_event")
    public EventResource takeEventContents(@RequestParam UUID idd) {
        return eventService.getEvent(idd);
    }

    @ApiOperation(value = "Get events order by time", response = List.class)
    @GetMapping("/get_events")
    public List<EventResource> getEvents() {
        return eventService.getEvents();
    }

    @ApiOperation(value = "Give point to an event over 5", response = EventResource.class)
    @GetMapping("/give_point")
    public EventResource givePoint(@RequestHeader ("Authorization") String token, @RequestParam UUID id, @RequestParam Double score) {
        try {
            jwtUserChecker.resolveBasicToken(token);
        } catch (IllegalAccessException e){

        }
        return eventService.givePoint(id,score);
    }

}
