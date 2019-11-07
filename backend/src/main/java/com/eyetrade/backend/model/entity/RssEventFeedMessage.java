package com.eyetrade.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Emir Gökdemir
 * on 7 Kas 2019
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@Table(name="rss_event_feed_message")
public class RssEventFeedMessage {
    private String title;
    private  String link;
    private String description;
    private String author;
    private String pubDate;
}
