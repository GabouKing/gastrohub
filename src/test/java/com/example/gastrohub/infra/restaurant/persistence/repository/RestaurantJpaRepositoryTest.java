package com.example.gastrohub.infra.restaurant.persistence.repository;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.repository.RestaurantJpaRepository;
import com.example.gastrohub.infra.persistence.repository.UserJpaRepository;
import com.example.gastrohub.infra.persistence.role.entity.RoleJpaEntity;
import com.example.gastrohub.infra.persistence.role.repository.RoleJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RestaurantJpaRepositoryTest {

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Test
    @DisplayName("Should find restaurant by id")
    void shouldFindRestaurantById() {
        UserJpaEntity user = ownerUser("joao1@email.com");
        RestaurantJpaEntity restaurant = restaurantJpaRepository.save(restaurant("Pizzaria Italia", "Rua A", user));

        Optional<RestaurantJpaEntity> result = restaurantJpaRepository.findById(restaurant.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(restaurant.getId());
        assertThat(result.get().getName()).isEqualTo("Pizzaria Italia");
    }

    @Test
    @DisplayName("Should return empty when restaurant id does not exist")
    void shouldReturnEmptyWhenIdDoesNotExist() {
        Optional<RestaurantJpaEntity> result = restaurantJpaRepository.findById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should find restaurant by name")
    void shouldFindRestaurantByName() {
        UserJpaEntity user = ownerUser("joao2@email.com");
        restaurantJpaRepository.save(restaurant("Burger House", "Rua B", user));

        Optional<RestaurantJpaEntity> result = restaurantJpaRepository.findByName("Burger House");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Burger House");
    }

    @Test
    @DisplayName("Should return empty when restaurant name does not exist")
    void shouldReturnEmptyWhenNameDoesNotExist() {
        Optional<RestaurantJpaEntity> result = restaurantJpaRepository.findByName("Restaurante Inexistente");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return all restaurants")
    void shouldReturnAllRestaurants() {
        UserJpaEntity user = ownerUser("joao3@email.com");
        restaurantJpaRepository.save(restaurant("Restaurante 1", "Rua A", user));
        restaurantJpaRepository.save(restaurant("Restaurante 2", "Rua B", user));

        List<RestaurantJpaEntity> restaurants = restaurantJpaRepository.findAll();

        assertThat(restaurants)
                .hasSize(2)
                .extracting(RestaurantJpaEntity::getName)
                .containsExactlyInAnyOrder("Restaurante 1", "Restaurante 2");
    }

    @Test
    @DisplayName("Should return empty list when there are no restaurants")
    void shouldReturnEmptyListWhenDatabaseIsEmpty() {
        List<RestaurantJpaEntity> restaurants = restaurantJpaRepository.findAll();

        assertThat(restaurants).isEmpty();
    }

    @Test
    @DisplayName("Should return true when a restaurant with the given name exists")
    void shouldReturnTrueWhenRestaurantNameExists() {
        UserJpaEntity user = ownerUser("joao4@email.com");
        restaurantJpaRepository.save(restaurant("Outback", "Av. Paulista", user));

        Boolean exists = restaurantJpaRepository.existsByName("Outback");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when no restaurant with the given name exists")
    void shouldReturnFalseWhenRestaurantNameDoesNotExist() {
        Boolean exists = restaurantJpaRepository.existsByName("Burger King");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should find restaurants by partial name")
    void shouldFindRestaurantsByPartialName() {
        UserJpaEntity user = ownerUser("joao5@email.com");
        restaurantJpaRepository.save(restaurant("Burger House", "Rua A", user));
        restaurantJpaRepository.save(restaurant("Super Burger", "Rua B", user));
        restaurantJpaRepository.save(restaurant("Pizzaria Italia", "Rua C", user));

        List<RestaurantJpaEntity> result = restaurantJpaRepository.findByNameContaining("Burger");

        assertThat(result)
                .hasSize(2)
                .extracting(RestaurantJpaEntity::getName)
                .containsExactlyInAnyOrder("Burger House", "Super Burger");
    }

    @Test
    @DisplayName("Should return empty list when no restaurant matches partial name")
    void shouldReturnEmptyListWhenNoRestaurantMatchesPartialName() {
        List<RestaurantJpaEntity> result = restaurantJpaRepository.findByNameContaining("Sushi");

        assertThat(result).isEmpty();
    }

    private UserJpaEntity ownerUser(String email) {
        var role = roleJpaRepository.save(new RoleJpaEntity(null, "USER_OWNER", "Dono de restaurante"));

        return userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("Joao")
                        .email(email)
                        .login(email.substring(0, email.indexOf("@")))
                        .password("123456")
                        .role(role)
                        .build()
        );
    }

    private RestaurantJpaEntity restaurant(String name, String address, UserJpaEntity user) {
        return RestaurantJpaEntity.builder()
                .name(name)
                .address(address)
                .openingHours("08:00-22:00")
                .cuisineType(CuisineType.ITALIAN)
                .user(user)
                .build();
    }
}
