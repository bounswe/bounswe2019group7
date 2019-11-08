package com.eyetrade.backend.controller;

import com.eyetrade.backend.model.entity.EventRssFeed;
import com.eyetrade.backend.model.resource.EventResource;
import com.eyetrade.backend.service.EventRssReaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "Rss read elements", response = EventRssFeed.class)
    @GetMapping("/check")
    public EventRssFeed checkRss() {
        return rssReaderService.readAndSaveFeed();
    }

    @ApiOperation(value = "Rss element description", response = String.class)
    @GetMapping("/get_event")
    public String takeEventContents(@RequestParam UUID idd) {
        return rssReaderService.getEvent(idd);
    }

    @ApiOperation(value = "Get events order by time", response = String.class)
    @GetMapping("/get_events")
    public List<EventResource> getEvents() {
        return rssReaderService.getEvents();
    }

}
