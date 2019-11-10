package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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

    @Column(name = "addition_date")
    private Date additionDate;

    @Column(name = "last_modified")
    private Date lastChangeDate;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_surname")
    private String authorSurname;

    @NotNull
    @Column(name = "author_email")
    @Email
    private String authorEmail;

    private Double score=4.00;

    @Column(name = "score_number")
    private Integer givenScoreCount =0;
}
