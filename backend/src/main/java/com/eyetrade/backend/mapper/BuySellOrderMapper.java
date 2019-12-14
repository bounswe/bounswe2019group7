package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.dto.BuySellOrderDto;
import com.eyetrade.backend.model.entity.BuySellOrder;
import com.eyetrade.backend.model.resource.transaction.BuySellOrderResource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE,uses = UserMapper.class)
public interface BuySellOrderMapper extends Converter<BuySellOrderDto, BuySellOrder, BuySellOrderResource> {

    List<BuySellOrderResource> entityToResource(List<BuySellOrder> annotations);

    @InheritInverseConfiguration(name = "dtoToEntity")
    void dtoToEntityForUpdate(BuySellOrderDto dto, @MappingTarget BuySellOrder entity);

    BuySellOrder resourceToEntity(BuySellOrderResource resource);
}
