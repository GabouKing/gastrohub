package com.example.gastrohub.infra.persistence.role.repository;

import com.example.gastrohub.infra.persistence.role.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, Long> {
    Optional<RoleJpaEntity> findByName(String name);
    boolean existsByName(String name);
}