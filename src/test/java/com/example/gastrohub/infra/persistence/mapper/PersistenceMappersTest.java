package com.example.gastrohub.infra.persistence.mapper;

import com.example.gastrohub.domain.menuitem.MenuItem;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.domain.user.UserRole;
import com.example.gastrohub.infra.persistence.entity.MenuItemJpaEntity;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.role.entity.RoleJpaEntity;
import com.example.gastrohub.infra.persistence.role.mapper.RolePersistenceMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceMappersTest {

    @Test
    void shouldMapMenuItemBetweenDomainAndEntity() {
        var mapper = new MenuItemPersistenceMapper();
        var domain = new MenuItem(
                1L,
                10L,
                "Burger",
                "Burger artesanal",
                BigDecimal.valueOf(39.90),
                true,
                "/images/burger.png"
        );

        var entity = mapper.toEntity(domain);
        var mappedDomain = mapper.toDomain(entity);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getRestaurantId()).isEqualTo(10L);
        assertThat(entity.getPrice()).isEqualByComparingTo("39.90");
        assertThat(mappedDomain.getName()).isEqualTo("Burger");
        assertThat(mappedDomain.getPhotoPath()).isEqualTo("/images/burger.png");
    }

    @Test
    void shouldMapRestaurantBetweenDomainAndEntity() {
        var mapper = new RestaurantPersistenceMapper();
        var user = UserJpaEntity.builder()
                .id(20L)
                .name("Owner")
                .email("owner@email.com")
                .login("owner")
                .password("123456")
                .role(UserRole.USER_OWNER)
                .build();
        var domain = new Restaurant(
                1L,
                "Burger House",
                "Rua das Flores, 123",
                CuisineType.BRAZILIAN,
                "08:00-22:00",
                20L
        );

        var entity = mapper.toEntity(domain, user);
        var mappedDomain = mapper.toDomain(entity);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getCuisineType()).isEqualTo(CuisineType.BRAZILIAN);
        assertThat(entity.getUser().getId()).isEqualTo(20L);
        assertThat(mappedDomain.getName()).isEqualTo("Burger House");
        assertThat(mappedDomain.getCuisineType()).isEqualTo(CuisineType.BRAZILIAN);
        assertThat(mappedDomain.getUserId()).isEqualTo(20L);
    }

    @Test
    void shouldMapUserBetweenDomainAndEntity() {
        var mapper = new UserPersistenceMapper(new RestaurantPersistenceMapper());
        var domain = new User(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                UserRole.USER_OWNER,
                List.of()
        );

        var entity = mapper.toEntity(domain);
        var mappedDomain = mapper.toDomain(entity);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getRole()).isEqualTo(UserRole.USER_OWNER);
        assertThat(mappedDomain.getEmail()).isEqualTo("vinicius@email.com");
        assertThat(mappedDomain.getLogin()).isEqualTo("vinicius");
    }

    @Test
    void shouldMapRoleBetweenDomainAndEntity() {
        var mapper = new RolePersistenceMapper();
        var domain = new Role(1L, "OWNER", "Dono do restaurante");

        var entity = mapper.toEntity(domain);
        var mappedDomain = mapper.toDomain(entity);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("OWNER");
        assertThat(mappedDomain.getDescription()).isEqualTo("Dono do restaurante");
    }

    @Test
    void shouldReadJpaEntityFieldsCreatedWithNoArgsConstructor() {
        var menuItemEntity = new MenuItemJpaEntity();
        menuItemEntity.setId(1L);
        menuItemEntity.setName("Pizza");
        menuItemEntity.setRestaurantId(10L);

        var restaurantEntity = RestaurantJpaEntity.builder()
                .id(2L)
                .name("Pizzaria Central")
                .address("Rua das Flores, 123")
                .cuisineType(CuisineType.ITALIAN)
                .openingHours("08:00-22:00")
                .user(new UserJpaEntity())
                .build();

        var userEntity = new UserJpaEntity();
        userEntity.setId(3L);
        userEntity.setEmail("user@email.com");

        var roleEntity = new RoleJpaEntity();
        roleEntity.setId(4L);
        roleEntity.setName("ADMIN");

        assertThat(menuItemEntity.getRestaurantId()).isEqualTo(10L);
        assertThat(restaurantEntity.getName()).isEqualTo("Pizzaria Central");
        assertThat(userEntity.getEmail()).isEqualTo("user@email.com");
        assertThat(roleEntity.getName()).isEqualTo("ADMIN");
    }
}
