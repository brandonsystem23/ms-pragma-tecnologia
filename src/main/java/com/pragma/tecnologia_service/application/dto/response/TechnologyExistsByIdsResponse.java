package com.pragma.tecnologia_service.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TechnologyExistsByIdsResponse(
        List<Long> existingIds
) {
}
