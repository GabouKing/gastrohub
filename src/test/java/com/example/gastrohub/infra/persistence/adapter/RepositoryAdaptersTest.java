package com.example.gastrohub.infra.persistence.adapter;

import com.example.gastrohub.domain.menuitem.MenuItem;
import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import com.example.gastrohub.domain.role.Role;
import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.infra.persistence.entity.MenuItemJpaEntity;
import com.example.gastrohub.infra.persistence.entity.RestaurantJpaEntity;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.mapper.MenuItemPersistenceMapper;
import com.example.gastrohub.infra.persistence.mapper.RestaurantPersistenceMapper;
import com.example.gastrohub.infra.persistence.mapper.UserPersistenceMapper;
import com.example.gastrohub.infra.persistence.repository.MenuItemJpaRepository;
import com.example.gastrohub.infra.persistence.repository.RestaurantJpaRepository;
import com.example.gastrohub.infra.persistence.repository.UserJpaRepository;
import com.example.gastrohub.infra.persistence.role.adapter.RoleRepositoryAdapter;
import com.example.gastrohub.infra.persistence.role.entity.RoleJpaEntity;
import com.example.gastrohub.infra.persistence.role.mapper.RolePersistenceMapper;
import com.example.gastrohub.infra.persistence.role.repository.RoleJpaRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RepositoryAdaptersTest {

    @Test
    void shouldSaveAndFindMenuItem() {
        var repository = mock(MenuItemJpaRepository.class);
        var mapper = mock(MenuItemPersistenceMapper.class);
        var adapter = new MenuItemRepositoryAdapter(repository, mapper);
        var domain = menuItem();
        var entity = menuItemEntity();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByRestaurantId(10L)).thenReturn(List.of(entity));

        var saved = adapter.save(domain);
        var found = adapter.findById(1L);
        var list = adapter.findByRestaurantId(10L);

        assertThat(saved).isSameAs(domain);
        assertThat(found).containsSame(domain);
        assertThat(list).containsExactly(domain);
        verify(repository).save(entity);
        verify(repository).findById(1L);
        verify(repository).findByRestaurantId(10L);
    }

    @Test
    void shouldDeleteMenuItem() {
        var repository = mock(MenuItemJpaRepository.class);
        var adapter = new MenuItemRepositoryAdapter(repository, mock(MenuItemPersistenceMapper.class));

        adapter.deleteById(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void shouldSaveFindListAndDeleteRestaurant() {
        var repository = mock(RestaurantJpaRepository.class);
        var userRepository = mock(UserJpaRepository.class);
        var mapper = mock(RestaurantPersistenceMapper.class);
        var adapter = new RestaurantRepositoryAdapter(repository, userRepository, mapper);
        var domain = restaurant();
        var entity = restaurantEntity();
        var userEntity = userEntity();

        when(userRepository.findById(20L)).thenReturn(Optional.of(userEntity));
        when(mapper.toEntity(domain, userEntity)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByName("Burger House")).thenReturn(Optional.of(entity));
        when(repository.findAll()).thenReturn(List.of(entity));
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsByName("Burger House")).thenReturn(true);

        assertThat(adapter.save(domain)).isSameAs(domain);
        assertThat(adapter.findById(1L)).containsSame(domain);
        assertThat(adapter.findByName("Burger House")).containsSame(domain);
        assertThat(adapter.findAll()).containsExactly(domain);
        assertThat(adapter.existsById(1L)).isTrue();
        assertThat(adapter.existsByName("Burger House")).isTrue();

        adapter.delete(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldSaveFindListAndDeleteUser() {
        var repository = mock(UserJpaRepository.class);
        var mapper = mock(UserPersistenceMapper.class);
        var adapter = new UserRepositoryAdapter(repository, mapper);
        var domain = user();
        var entity = userEntity();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByEmail("vinicius@email.com")).thenReturn(Optional.of(entity));
        when(repository.findAll()).thenReturn(List.of(entity));
        when(repository.existsById(1L)).thenReturn(true);

        assertThat(adapter.save(domain)).isSameAs(domain);
        assertThat(adapter.findById(1L)).containsSame(domain);
        assertThat(adapter.findByEmail("vinicius@email.com")).containsSame(domain);
        assertThat(adapter.findAll()).containsExactly(domain);
        assertThat(adapter.existsById(1L)).isTrue();

        adapter.deleteById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldSaveFindListAndDeleteRole() {
        var repository = mock(RoleJpaRepository.class);
        var mapper = mock(RolePersistenceMapper.class);
        var adapter = new RoleRepositoryAdapter(repository, mapper);
        var domain = new Role(1L, "OWNER", "Dono do restaurante");
        var entity = new RoleJpaEntity(1L, "OWNER", "Dono do restaurante");

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(domain);
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(repository.findByName("OWNER")).thenReturn(Optional.of(entity));
        when(repository.findAll()).thenReturn(List.of(entity));
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.existsByName("OWNER")).thenReturn(true);

        assertThat(adapter.save(domain)).isSameAs(domain);
        assertThat(adapter.findById(1L)).containsSame(domain);
        assertThat(adapter.findByName("OWNER")).containsSame(domain);
        assertThat(adapter.findAll()).containsExactly(domain);
        assertThat(adapter.existsById(1L)).isTrue();
        assertThat(adapter.existsByName("OWNER")).isTrue();

        adapter.deleteById(1L);
        verify(repository).deleteById(1L);
    }

    private MenuItem menuItem() {
        return new MenuItem(
                1L,
                10L,
                "Burger",
                "Burger artesanal",
                BigDecimal.valueOf(39.90),
                true,
                "/images/burger.png"
        );
    }

    private MenuItemJpaEntity menuItemEntity() {
        return new MenuItemJpaEntity(
                1L,
                10L,
                "Burger",
                "Burger artesanal",
                BigDecimal.valueOf(39.90),
                true,
                "/images/burger.png"
        );
    }

    private Restaurant restaurant() {
        return new Restaurant(
                1L,
                "Burger House",
                "Rua das Flores, 123",
                CuisineType.BRAZILIAN,
                "08:00-22:00",
                20L
        );
    }

    private RestaurantJpaEntity restaurantEntity() {
        return RestaurantJpaEntity.builder()
                .id(1L)
                .name("Burger House")
                .address("Rua das Flores, 123")
                .cuisineType(CuisineType.BRAZILIAN)
                .openingHours("08:00-22:00")
                .user(userEntity())
                .build();
    }

    private User user() {
        return new User(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                new Role(3L, "USER_OWNER", "Dono de restaurante"),
                List.of()
        );
    }

    private UserJpaEntity userEntity() {
        return new UserJpaEntity(
                1L,
                "Vinicius",
                "vinicius@email.com",
                "vinicius",
                "123456",
                new RoleJpaEntity(3L, "USER_OWNER", "Dono de restaurante"),
                List.of()
        );
    }
}
