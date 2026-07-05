package com.example.gastrohub.infra.restaurant.persistence.repository;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.repository.UserJpaRepository;
import com.example.gastrohub.infra.restaurant.persistence.entity.RestaurantJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
@ActiveProfiles("test")
class RestaurantJpaRepositoryTest {

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("Should find restaurant by id")
    void shouldFindRestaurantById() {

        UserJpaEntity user = userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("João")
                        .email("joao@email.com")
                        .password("123456")
                        .build()
        );

        RestaurantJpaEntity restaurant = restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Pizzaria Itália")
                        .address("Rua A")
                        .openingHours("08:00 - 22:00")
                        .cuisineType(CuisineType.ITALIAN)
                        .user(user)
                        .build()
        );

        Optional<RestaurantJpaEntity> result =
                restaurantJpaRepository.findById(restaurant.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(restaurant.getId());
        assertThat(result.get().getName()).isEqualTo("Pizzaria Itália");
    }

    @Test
    @DisplayName("Should return empty when restaurant id does not exist")
    void shouldReturnEmptyWhenIdDoesNotExist() {

        Optional<RestaurantJpaEntity> result =
                restaurantJpaRepository.findById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find restaurant by name")
    void shouldFindRestaurantByName() {

        UserJpaEntity user = userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("João")
                        .email("joao@email.com")
                        .password("123456")
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Burger House")
                        .address("Rua B")
                        .openingHours("10:00 - 23:00")
                        .cuisineType(CuisineType.ITALIAN)
                        .user(user)
                        .build()
        );

        Optional<RestaurantJpaEntity> result =
                restaurantJpaRepository.findByName("Burger House");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Burger House");
    }

    @Test
    @DisplayName("Should return empty when restaurant name does not exist")
    void shouldReturnEmptyWhenNameDoesNotExist() {

        Optional<RestaurantJpaEntity> result =
                restaurantJpaRepository.findByName("Restaurante Inexistente");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return all restaurants")
    void shouldReturnAllRestaurants() {

        UserJpaEntity user = userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("João")
                        .email("joao@email.com")
                        .password("123456")
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Restaurante 1")
                        .address("Rua A")
                        .openingHours("08:00 - 18:00")
                        .cuisineType(CuisineType.BRAZILIAN)
                        .user(user)
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Restaurante 2")
                        .address("Rua B")
                        .openingHours("09:00 - 20:00")
                        .cuisineType(CuisineType.ITALIAN)
                        .user(user)
                        .build()
        );

        List<RestaurantJpaEntity> restaurants =
                restaurantJpaRepository.findAll();

        assertThat(restaurants)
                .hasSize(2)
                .extracting(RestaurantJpaEntity::getName)
                .containsExactlyInAnyOrder(
                        "Restaurante 1",
                        "Restaurante 2"
                );
    }

    @Test
    @DisplayName("Should return empty list when there are no restaurants")
    void shouldReturnEmptyListWhenDatabaseIsEmpty() {

        List<RestaurantJpaEntity> restaurants =
                restaurantJpaRepository.findAll();

        assertThat(restaurants).isEmpty();
    }

    @Test
    @DisplayName("Should return true when a restaurant with the given name exists")
    void shouldReturnTrueWhenRestaurantNameExists() {
        // Arrange
        UserJpaEntity user = userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("João")
                        .email("joao@email.com")
                        .password("123456")
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Outback")
                        .address("Av. Paulista")
                        .cuisineType(CuisineType.BRAZILIAN)
                        .openingHours("08:00 - 22:00")
                        .user(user)
                        .build()
        );

        // Act
        Boolean exists = restaurantJpaRepository.existsByName("Outback");

        // Assert
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when no restaurant with the given name exists")
    void shouldReturnFalseWhenRestaurantNameDoesNotExist() {
        // Act
        Boolean exists = restaurantJpaRepository.existsByName("Burger King");

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should find restaurants by partial name")
    void shouldFindRestaurantsByPartialName() {

        UserJpaEntity user = userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("João")
                        .email("joao@email.com")
                        .password("123456")
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Burger House")
                        .address("Rua A")
                        .openingHours("08:00 - 22:00")
                        .cuisineType(CuisineType.BRAZILIAN)
                        .user(user)
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Super Burger")
                        .address("Rua B")
                        .openingHours("09:00 - 23:00")
                        .cuisineType(CuisineType.ITALIAN)
                        .user(user)
                        .build()
        );

        restaurantJpaRepository.save(
                RestaurantJpaEntity.builder()
                        .name("Pizzaria Itália")
                        .address("Rua C")
                        .openingHours("10:00 - 23:00")
                        .cuisineType(CuisineType.ITALIAN)
                        .user(user)
                        .build()
        );

        List<RestaurantJpaEntity> result =
                restaurantJpaRepository.findByNameContaining("Burger");

        assertThat(result)
                .hasSize(2)
                .extracting(RestaurantJpaEntity::getName)
                .containsExactlyInAnyOrder(
                        "Burger House",
                        "Super Burger"
                );
    }

    @Test
    @DisplayName("Should return empty list when no restaurant matches partial name")
    void shouldReturnEmptyListWhenNoRestaurantMatchesPartialName() {

        List<RestaurantJpaEntity> result =
                restaurantJpaRepository.findByNameContaining("Sushi");

        assertThat(result).isEmpty();
    }
}