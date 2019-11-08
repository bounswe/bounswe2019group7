package com.eyetrade.backend.service;

import com.eyetrade.backend.model.entity.EventRssFeed;
import com.eyetrade.backend.model.entity.EventRssFeedMessage;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import static com.eyetrade.backend.constants.RssFeedConstants.EVENT_RSS_URL;

/**
 * Created by Emir Gökdemir
 * on 7 Kas 2019
 */

@Service
public class EventRssReaderService {


    public EventRssFeed readFeed() {

        SyndFeed feed;
        try {
            URL feedSource = new URL(EVENT_RSS_URL);
            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(feedSource));
        }catch (IOException e){
            throw new RuntimeException();
        }catch (FeedException fe){
            throw new RuntimeException();
        }
        Date formattedDate=new Date();
        EventRssFeed eventFeed=new EventRssFeed();

        //Setting our eventFeed data from parsed data.
        eventFeed.setDescription(feed.getDescription());
        eventFeed.setLink(feed.getLink());
        eventFeed.setTitle(feed.getTitle());
        eventFeed.setAdditionDate(formattedDate);

        for(Object object:feed.getEntries()){
            //cascading
            SyndEntryImpl event=(SyndEntryImpl)object;

            EventRssFeedMessage eventFeedMessage=new EventRssFeedMessage();

            //Setting our eventFeedMessage data from parsed object.
            eventFeedMessage.setGuid(UUID.fromString(event.getUri()));
            eventFeedMessage.setLink(event.getLink());
            eventFeedMessage.setTitle(event.getTitle());
            eventFeedMessage.setDescription(event.getDescription().toString());
            eventFeedMessage.setPubDate(formattedDate);
            eventFeedMessage.setFeed(eventFeed);
        }
        return eventFeed;
    }
}
