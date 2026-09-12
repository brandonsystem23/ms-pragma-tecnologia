package com.pragma.tecnologia_service.application.mapper;

import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyDtoMapper {

    TechnologyCommand toCommand(TechnologyRequest request);

    TechnologyResponse toResponse(Technology technology);
}
