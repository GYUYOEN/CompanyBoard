package com.gue.board.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface GenericMapper<DTO, Entity> { //entity 와 dto를 자동변형

    DTO toDTO(Entity entity);

    Entity toEntity(DTO dto);

    List<DTO> toDTOList(List<Entity> entityList);
    List<Entity> toEntityList(List<DTO> dtoList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDTO(DTO dto, @MappingTarget Entity entity);
}
