package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(length = 1024)
    private String content;

    @Column(name = "addition date")
    private Date additionDate;

    @Column(name = "last modified")
    private Date lastChangeDate;

    @Column(name = "author id")
    private UUID authorId;

    private Double score=4.00;

    @Column(name = "score_number")
    private Integer givenScoreCount =0;
}
