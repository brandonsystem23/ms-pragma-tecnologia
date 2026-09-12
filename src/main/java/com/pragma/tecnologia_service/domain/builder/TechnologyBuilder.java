package com.pragma.tecnologia_service.domain.builder;

import com.pragma.tecnologia_service.domain.model.Technology;
import com.pragma.tecnologia_service.domain.model.TechnologyCommand;

public final class TechnologyBuilder {

    private TechnologyBuilder() {

    }

    public static Technology buildTechnology(TechnologyCommand technologyCommand) {
        return Technology.builder()
                .name(technologyCommand.name())
                .description(technologyCommand.description())
                .build();
    }
}
