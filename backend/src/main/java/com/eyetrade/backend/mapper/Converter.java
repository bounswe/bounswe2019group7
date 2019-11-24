package com.eyetrade.backend.mapper;

/**
 * DTO-Entity-Resource Converter Interface.
 */
public interface Converter<DTO, Entity, Resource> {

    Resource entityToResource(Entity entity);

    Entity dtoToEntity(DTO dto);
}
