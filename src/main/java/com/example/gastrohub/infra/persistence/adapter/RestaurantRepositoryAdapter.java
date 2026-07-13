package com.example.gastrohub.infra.persistence.adapter;

import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.RestaurantGateway;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.repository.UserJpaRepository;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import com.example.gastrohub.infra.persistence.mapper.RestaurantPersistenceMapper;
import com.example.gastrohub.infra.persistence.repository.RestaurantJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryAdapter implements RestaurantGateway {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RestaurantPersistenceMapper mapper;


    @Override
    public Restaurant save(Restaurant restaurant) {
        UserJpaEntity user = userJpaRepository.findById(restaurant.getUserId())
                .orElseThrow(() -> new UserNotFound(restaurant.getUserId()));

        RestaurantJpaEntity entity = mapper.toEntity(restaurant, user);
        RestaurantJpaEntity saved = restaurantJpaRepository.save(entity);


        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Restaurant> findByName(String name) {
        return restaurantJpaRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Restaurant> findByNameContaining(String name) {
        return restaurantJpaRepository.findByNameContaining(name)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Restaurant> findAll() {
        return restaurantJpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Long id) {
        restaurantJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return restaurantJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return restaurantJpaRepository.existsByName(name);
    }
}