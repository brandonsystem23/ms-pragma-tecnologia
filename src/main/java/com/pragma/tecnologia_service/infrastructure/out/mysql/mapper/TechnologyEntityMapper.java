package com.pragma.tecnologia_service.infrastructure.out.mysql.mapper;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.infrastructure.out.mysql.entity.TechnologyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyEntityMapper {

    Technology toDomain(TechnologyEntity technologyEntity);

    TechnologyEntity toEntity(Technology technology);

}