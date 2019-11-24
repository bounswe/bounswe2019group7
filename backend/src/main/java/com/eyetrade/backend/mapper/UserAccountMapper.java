package com.eyetrade.backend.mapper;

import com.eyetrade.backend.model.entity.UserTradingAccount;
import com.eyetrade.backend.model.resource.transaction.UserAccountResource;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserAccountMapper {
    UserAccountResource entityToResource(UserTradingAccount account);
}
