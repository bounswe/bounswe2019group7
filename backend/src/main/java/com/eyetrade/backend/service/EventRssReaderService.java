package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.Event;
import com.eyetrade.backend.model.entity.EventRssFeed;
import com.eyetrade.backend.repository.EventRepository;
import com.eyetrade.backend.repository.EventRssFeedRepository;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.eyetrade.backend.constants.ErrorConstants.EVENTS_CANNOT_BE_UPLOADED;
import static com.eyetrade.backend.constants.EventConstants.EVENT_RSS_URL;

/**
 * Created by Emir Gökdemir
 * on 7 Kas 2019
 */

@Service
public class EventRssReaderService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRssFeedRepository feedRepository;

    @Transactional
    @Scheduled(cron = "0 0/12 * * * *")
    public EventRssFeed readAndSaveFeed() throws FeedException {
        SyndFeed feed;

        //delete data exist more than one day.
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        cal.add(Calendar.DATE, -1);
        Date previousDay = cal.getTime();
        eventRepository.deleteByAdditionDateBefore(previousDay);
        feedRepository.deleteByAdditionDateBefore(previousDay);

        try {
            URL feedSource = new URL(EVENT_RSS_URL);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(feedSource));
        } catch (IOException e) {
            throw new FeedException(EVENTS_CANNOT_BE_UPLOADED);
        } catch (FeedException fe) {
            throw new FeedException(EVENTS_CANNOT_BE_UPLOADED);
        }
        Date date = new Date();
        EventRssFeed eventFeed = new EventRssFeed();

        //Setting our eventFeed data from parsed data.
        eventFeed.setDescription(feed.getDescription());
        eventFeed.setLink(feed.getLink());
        eventFeed.setTitle(feed.getTitle());
        eventFeed.setAdditionDate(date);
        feedRepository.save(eventFeed);
        eventFeed = readAndSaveMessages(eventFeed, feed);
        return eventFeed;
    }

    @Transactional
    protected EventRssFeed readAndSaveMessages(EventRssFeed eventFeed, SyndFeed feed) {
        for (Object object : feed.getEntries()) {
            //cascading
            SyndEntryImpl syndEntry = (SyndEntryImpl) object;
            UUID guid = UUID.fromString(syndEntry.getUri());
            if (eventRepository.existsByGuid(guid)) {
                break;
            }
            Event event = new Event();
            //Setting our event data from parsed object.
            event.setGuid(guid);
            event.setLink(syndEntry.getLink());
            event.setTitle(syndEntry.getTitle());
            event.setContent(syndEntry.getDescription().getValue());
            event.setAdditionDate(eventFeed.getAdditionDate());
            event.setFeed(eventFeed);
            eventRepository.saveAndFlush(event);
        }
        return eventFeed;
    }

}
