package com.eyetrade.backend.model.resource.prediction;

import lombok.Data;

import javax.annotation.Resource;

@Resource
@Data
public class PredictionCountResource {

    private long totalCount;

    private long successCount;

    private long failCount;

    private long futureCount;

}
