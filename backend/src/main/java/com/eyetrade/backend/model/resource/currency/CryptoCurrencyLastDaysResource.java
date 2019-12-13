package com.eyetrade.backend.model.resource.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import java.util.List;

@Resource
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyLastDaysResource {

    List<CryptoCurrencyConverterResource> resources;
    String startDate;
    Integer dayCount;

}
