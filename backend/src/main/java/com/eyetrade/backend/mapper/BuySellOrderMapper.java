package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.BuySellOrderDto;
import com.eyetrade.backend.model.entity.BuySellOrder;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BuySellOrderMapper extends Converter<BuySellOrderDto, BuySellOrder, BuySellOrderResource> {
}
