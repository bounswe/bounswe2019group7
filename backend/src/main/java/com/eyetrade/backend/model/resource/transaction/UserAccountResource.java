package com.eyetrade.backend.model.resource.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by Emir Gökdemir
 * on 24 Kas 2019
 */
@Resource
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAccountResource {

    private UUID id;

    private Double tryAmount;

    private Double usdAmount;

    private Double eurAmount;

    private Double cnyAmount;

    private Double jpyAmount;

    private Double gbpAmount;
}
