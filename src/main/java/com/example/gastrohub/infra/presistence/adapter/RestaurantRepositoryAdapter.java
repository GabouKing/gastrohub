package com.example.gastrohub.infra.presistence.adapter;

import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.infra.presistence.repository.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryAdapter implements RestaurantGateway {
    private final RestaurantJpaRepository repository;

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
