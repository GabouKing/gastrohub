package com.example.gastrohub.infra.persistence.role.adapter;

import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.role.RoleGateway;
import com.example.gastrohub.infra.persistence.role.mapper.RolePersistenceMapper;
import com.example.gastrohub.infra.persistence.role.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryAdapter implements RoleGateway {
    private final RoleJpaRepository repository;
    private final RolePersistenceMapper mapper;

    @Override
    public Role save(Role role) {
        var entity = mapper.toEntity(role);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }
}