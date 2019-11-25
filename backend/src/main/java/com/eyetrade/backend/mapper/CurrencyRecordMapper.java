package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.entity.CurrencyRecord;
import com.eyetrade.backend.model.resource.currency.CurrencyRecordResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CurrencyRecordMapper {
    CurrencyRecordResource entityToResource(CurrencyRecord record);
}
