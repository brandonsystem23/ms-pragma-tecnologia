package com.pragma.tecnologia_service.infrastructure.out.mysql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("technology")
public class TechnologyEntity {

    @Id
    private Long id;

    private String name;

    private String description;

    private Boolean status;
}
