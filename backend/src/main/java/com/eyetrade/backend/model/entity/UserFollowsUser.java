package com.eyetrade.backend.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

@Data
@Entity
@Table(name="user_follows_user")
public class UserFollowsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "follower")
    private User follower;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "following")
    private User following;

    @NotNull
    @Column(name = "timestamp")
    private Long timestamp;

}
