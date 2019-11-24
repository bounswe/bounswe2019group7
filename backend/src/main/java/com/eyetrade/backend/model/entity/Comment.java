package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    private String title;

    @Column(length = 1024)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "created_date")
    private String createdDate;

    private Boolean isBelongToArticle; // if false then it is belong to event

    private UUID articleEventId;
}

