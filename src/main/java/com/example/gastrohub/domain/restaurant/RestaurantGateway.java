package com.example.gastrohub.domain.restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    Optional<Restaurant> findByName(String name);
    List<Restaurant> findAll();
    void delete(Long id);
    boolean existsById(Long id);
    boolean existsByName(String name);
}
