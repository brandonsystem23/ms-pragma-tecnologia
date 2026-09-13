package com.pragma.tecnologia_service.infrastructure.input.rest;

import com.pragma.tecnologia_service.application.dto.request.TechnologyIdsRequest;
import com.pragma.tecnologia_service.application.dto.request.TechnologyRequest;
import com.pragma.tecnologia_service.application.dto.response.TechnologyExistsByIdsResponse;
import com.pragma.tecnologia_service.application.dto.response.TechnologyResponse;
import com.pragma.tecnologia_service.application.handler.ITechnologyHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technology")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tecnologia", description = "Endpoint para gestion de tecnologias")
public class TechnologyController {

    private final ITechnologyHandler iTechnologyHandler;

    @PostMapping("/create")
    @Operation(summary = "Crear tecnologia", description = "Crear una tecnologia. Requiere rol ADMINISTRADOR")
    public Mono<TechnologyResponse> createTechnology(@RequestBody TechnologyRequest request
    ) {

        log.info("Solicitud para crear una tecnologia");

        return iTechnologyHandler.create(request);
    }

    @GetMapping("/list")
    @Operation(summary = "Listar tecnologias", description = "Listar tecnologias. Requiere rol ADMINISTRADOR")
    public Flux<TechnologyResponse> retrieveTechnology() {

        log.info("Petición para listar todas las tecnologias");

        return iTechnologyHandler.list();
    }

    @PostMapping("/exists-by-ids")
    @Operation(summary = "Consultar tecnologias existentes por ids",
            description = "Retorna los ids de las tecnologias que existen. Requiere rol ADMINISTRADOR")
    public Mono<TechnologyExistsByIdsResponse> existsByIds(@RequestBody TechnologyIdsRequest request) {

        log.info("Petición para validar tecnologías por ids");

        return iTechnologyHandler.existsByIds(request.ids());
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Obtener tecnologias por ids",
            description = "Retorna las tecnologías encontradas según la lista de ids. Requiere rol ADMINISTRADOR")
    public Flux<TechnologyResponse> findByIds(@RequestParam List<Long> ids) {

        log.info("Petición para obtener tecnologías por ids");

        return iTechnologyHandler.findByIds(ids);
    }
}
