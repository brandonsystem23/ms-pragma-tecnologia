package com.pragma.tecnologia_service.application.dto.request;

import java.util.List;

public record TechnologyIdsRequest(
        List<Long> ids
) {
}
