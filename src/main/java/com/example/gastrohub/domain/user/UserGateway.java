package com.example.gastrohub.domain.user;

import java.util.List;
import java.util.Optional;

public interface UserGateway {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
