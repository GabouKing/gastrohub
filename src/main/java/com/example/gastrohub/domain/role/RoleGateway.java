package com.example.gastrohub.domain.role;

import java.util.List;
import java.util.Optional;

public interface RoleGateway {
    Role save(Role role);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
}