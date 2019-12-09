package com.eyetrade.backend.model.entity;

import com.eyetrade.backend.constants.CommentType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 8 Ara 2019
 */

@Data
@Entity
@Table(name = "annotation")
public class Annotation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private UUID id;

    @Column(length = 1024)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //article or event
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    private UUID articleEventId;

    private Integer firstChar;

    private Integer lastChar;
}

