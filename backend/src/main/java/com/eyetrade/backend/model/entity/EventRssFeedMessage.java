package com.eyetrade.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 7 Kas 2019
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rss_event_feed_message")
public class EventRssFeedMessage {

    @Id
    private UUID guid;

    private String link;

    private String title;

    private String description;

    private Date pubDate;

    private String additionDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private EventRssFeed feed;
}
