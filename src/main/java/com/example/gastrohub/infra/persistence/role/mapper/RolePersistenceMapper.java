package com.example.gastrohub.infra.persistence.role.mapper;

import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.infra.persistence.role.entity.RoleJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class RolePersistenceMapper {
    public RoleJpaEntity toEntity(Role role) {
        return new RoleJpaEntity(role.getId(), role.getName(), role.getDescription());
    }

    public Role toDomain(RoleJpaEntity entity) {
        return new Role(entity.getId(), entity.getName(), entity.getDescription());
    }
}