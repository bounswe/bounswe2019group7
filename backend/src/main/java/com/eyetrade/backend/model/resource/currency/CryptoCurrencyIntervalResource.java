package com.eyetrade.backend.model.resource.currency;

import lombok.*;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyIntervalResource {

    List<CryptoCurrencyConverterResource> resources;
    String startDate;
    String endDate;

}
