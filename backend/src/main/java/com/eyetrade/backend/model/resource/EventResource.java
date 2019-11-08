package com.eyetrade.backend.model.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Emir Gökdemir
 * on 9 Kas 2019
 */

@Resource
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResource {
    private String title;
    private String content;
    private String stringDate;
}
