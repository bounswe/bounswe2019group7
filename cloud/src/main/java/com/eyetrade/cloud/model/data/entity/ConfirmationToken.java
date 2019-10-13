package com.eyetrade.cloud.model.data.entity;

import com.eyetrade.cloud.util.constants.TokenConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

import static com.eyetrade.cloud.util.constants.GeneralConstants.ID_LENGTH;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */
@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name = "confirmation_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"}),
        @UniqueConstraint(columnNames = {"confirmation_token"})
})
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private String identifier;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "type")
    private String type;

    @Column(name = "token_status")
    private String tokenStatus;

    private Date createdDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;

    public ConfirmationToken(User user, String type) {
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
        this.tokenStatus = TokenConstants.ACTIVE;
        this.type = type;
    }

}
