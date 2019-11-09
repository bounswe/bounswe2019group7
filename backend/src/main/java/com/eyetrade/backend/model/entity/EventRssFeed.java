package com.eyetrade.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

/**
 * Created by Emir Gökdemir
 * on 7 Kas 2019
 */

@Entity
@Data
@ToString
@AllArgsConstructor
@Table(name="rss_event_feed")
@NoArgsConstructor
public class EventRssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    private String title;

    private String link;

    private String description;

    private Date additionDate;
}
