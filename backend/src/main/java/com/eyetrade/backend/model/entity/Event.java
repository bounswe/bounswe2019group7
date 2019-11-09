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
@Table(name = "event")
public class Event {

    @Id
    @Column(name="id")
    private UUID guid;

    private String link;

    private String title;

    @Column(length = 1024)
    private String content;

    @Column(name="addition_date")
    private Date additionDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "feed_id")
    private EventRssFeed feed;

    private Double score=4.00;

    @Column(name = "score_number")
    private Integer givenScoreCount =0;
}
