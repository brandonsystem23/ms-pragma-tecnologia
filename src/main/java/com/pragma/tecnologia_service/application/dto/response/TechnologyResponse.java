package com.pragma.tecnologia_service.application.dto.response;

import lombok.Builder;

@Builder
public record TechnologyResponse(

        Long id,

        String name,

        String description
) {
}
