package com.example.gastrohub.infra.persistence;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.infra.persistence.entity.MenuItemJpaEntity;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.repository.MenuItemJpaRepository;
import com.example.gastrohub.infra.persistence.repository.RestaurantJpaRepository;
import com.example.gastrohub.infra.persistence.repository.UserJpaRepository;
import com.example.gastrohub.infra.persistence.role.entity.RoleJpaEntity;
import com.example.gastrohub.infra.persistence.role.repository.RoleJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CascadeDeleteJpaIntegrationTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private MenuItemJpaRepository menuItemJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should delete menu items when restaurant is deleted")
    void shouldDeleteMenuItemsWhenRestaurantIsDeleted() {
        var user = ownerUser("owner-restaurant-cascade@email.com", "USER_OWNER_RESTAURANT_CASCADE");
        var restaurant = restaurantJpaRepository.save(restaurant("Cascade Pizza", user));
        var menuItem = menuItemJpaRepository.save(menuItem("Pizza", restaurant));

        restaurantJpaRepository.deleteById(restaurant.getId());
        entityManager.flush();
        entityManager.clear();

        assertThat(restaurantJpaRepository.findById(restaurant.getId())).isEmpty();
        assertThat(menuItemJpaRepository.findById(menuItem.getId())).isEmpty();
    }

    @Test
    @DisplayName("Should delete restaurants and menu items when user is deleted")
    void shouldDeleteRestaurantsAndMenuItemsWhenUserIsDeleted() {
        var user = ownerUser("owner-user-cascade@email.com", "USER_OWNER_USER_CASCADE");
        var restaurant = restaurantJpaRepository.save(restaurant("Cascade Burger", user));
        var menuItem = menuItemJpaRepository.save(menuItem("Burger", restaurant));

        userJpaRepository.deleteById(user.getId());
        entityManager.flush();
        entityManager.clear();

        assertThat(userJpaRepository.findById(user.getId())).isEmpty();
        assertThat(restaurantJpaRepository.findById(restaurant.getId())).isEmpty();
        assertThat(menuItemJpaRepository.findById(menuItem.getId())).isEmpty();
    }

    private UserJpaEntity ownerUser(String email, String roleName) {
        var role = roleJpaRepository.save(new RoleJpaEntity(null, roleName, "Dono de restaurante"));

        return userJpaRepository.save(
                UserJpaEntity.builder()
                        .name("Owner")
                        .email(email)
                        .login(email.substring(0, email.indexOf("@")))
                        .password("Senha@123")
                        .role(role)
                        .build()
        );
    }

    private RestaurantJpaEntity restaurant(String name, UserJpaEntity user) {
        return RestaurantJpaEntity.builder()
                .name(name)
                .address("Rua das Flores, 123")
                .openingHours("08:00-22:00")
                .cuisineType(CuisineType.ITALIAN)
                .user(user)
                .build();
    }

    private MenuItemJpaEntity menuItem(String name, RestaurantJpaEntity restaurant) {
        var menuItem = new MenuItemJpaEntity();
        menuItem.setName(name);
        menuItem.setDescription(name + " artesanal");
        menuItem.setPrice(BigDecimal.valueOf(39.90));
        menuItem.setAvailableOnlyOnRestaurant(false);
        menuItem.setPhotoPath("/images/" + name.toLowerCase() + ".png");
        menuItem.setRestaurant(restaurant);
        return menuItem;
    }
}
