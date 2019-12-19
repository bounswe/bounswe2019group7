package com.eyetrade.backend.model.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.eyetrade.backend.constants.GeneralConstants.ID_LENGTH;

@Data
@Entity
@Table(name="prediction_count_of_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})})
public class PredictionCountOfUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", length = ID_LENGTH)
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "success_count")
    private long successCount;

    @NotNull
    @Column(name = "fail_count")
    private long failCount;

    public PredictionCountOfUser(UUID userId){
        this.userId = userId;
        this.successCount = 0;
        this.failCount = 0;
    }

}
