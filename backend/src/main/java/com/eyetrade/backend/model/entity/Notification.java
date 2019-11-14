package com.eyetrade.backend.model.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

@Data
@Entity
@Table(name="notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "notification_owner")
    private User notificationOwner;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @NotNull
    @Column(name = "notification_date")
    private Long notificationDate;

    @Column(name = "is_seen")
    private boolean isSeen;

}
