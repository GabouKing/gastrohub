package com.example.gastrohub.infra.restaurant.persistence.adapter;


import com.example.gastrohub.domain.restaurant.Restaurant;
import com.example.gastrohub.domain.user.exception.UserNotFound;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.repository.UserJpaRepository;
import com.example.gastrohub.infra.restaurant.persistence.entity.RestaurantJpaEntity;
import com.example.gastrohub.infra.restaurant.persistence.mapper.RestaurantPersistenceMapper;
import com.example.gastrohub.infra.restaurant.persistence.repository.RestaurantJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryAdapterTest {

    @Mock
    private RestaurantJpaRepository restaurantJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private RestaurantPersistenceMapper mapper;

    @InjectMocks
    private RestaurantRepositoryAdapter adapter;

    private RestaurantJpaEntity entity;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        entity = mock(RestaurantJpaEntity.class);
        restaurant = mock(Restaurant.class);
    }

    @Test
    @DisplayName("Should save restaurant successfully")
    void shouldSaveRestaurantSuccessfully() {

        Restaurant restaurant = mock(Restaurant.class);
        UserJpaEntity user = mock(UserJpaEntity.class);
        RestaurantJpaEntity entity = mock(RestaurantJpaEntity.class);
        RestaurantJpaEntity savedEntity = mock(RestaurantJpaEntity.class);
        Restaurant expected = mock(Restaurant.class);

        when(restaurant.getUserId()).thenReturn(1L);
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toEntity(restaurant, user)).thenReturn(entity);
        when(restaurantJpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(expected);

        Restaurant result = adapter.save(restaurant);

        assertEquals(expected, result);

        verify(userJpaRepository).findById(1L);
        verify(mapper).toEntity(restaurant, user);
        verify(restaurantJpaRepository).save(entity);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    @DisplayName("Should throw UserNotFound when user does not exist")
    void shouldThrowUserNotFoundWhenUserDoesNotExist() {

        Restaurant restaurant = mock(Restaurant.class);

        when(restaurant.getUserId()).thenReturn(1L);
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> adapter.save(restaurant));

        verify(restaurantJpaRepository, never()).save(any());
        verify(mapper, never()).toEntity(any(), any());
    }

    @Test
    @DisplayName("Should find restaurant by id")
    void shouldFindRestaurantById() {

        when(restaurantJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(restaurant);

        Optional<Restaurant> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());

        verify(restaurantJpaRepository).findById(1L);
        verify(mapper).toDomain(entity);
    }

    @Test
    @DisplayName("Should find restaurant by name")
    void shouldFindRestaurantByName() {

        when(restaurantJpaRepository.findByName("Pizza")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(restaurant);

        Optional<Restaurant> result = adapter.findByName("Pizza");

        assertTrue(result.isPresent());
        assertEquals(restaurant, result.get());

        verify(restaurantJpaRepository).findByName("Pizza");
        verify(mapper).toDomain(entity);
    }

    @Test
    @DisplayName("Should find restaurants by partial name")
    void shouldFindRestaurantsByPartialName() {

        when(restaurantJpaRepository.findByNameContaining("Pizza"))
                .thenReturn(List.of(entity));

        when(mapper.toDomain(entity))
                .thenReturn(restaurant);

        List<Restaurant> result = adapter.findByNameContaining("Pizza");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(restaurant, result.getFirst());

        verify(restaurantJpaRepository).findByNameContaining("Pizza");
        verify(mapper).toDomain(entity);
    }

    @Test
    @DisplayName("Should return empty list when no restaurants match partial name")
    void shouldReturnEmptyListWhenNoRestaurantsMatchPartialName() {

        when(restaurantJpaRepository.findByNameContaining("Pizza"))
                .thenReturn(List.of());

        List<Restaurant> result = adapter.findByNameContaining("Pizza");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(restaurantJpaRepository).findByNameContaining("Pizza");
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Should return all restaurants")
    void shouldReturnAllRestaurants() {

        RestaurantJpaEntity entity1 = mock(RestaurantJpaEntity.class);
        RestaurantJpaEntity entity2 = mock(RestaurantJpaEntity.class);

        Restaurant restaurant1 = mock(Restaurant.class);
        Restaurant restaurant2 = mock(Restaurant.class);

        when(restaurantJpaRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toDomain(entity1)).thenReturn(restaurant1);
        when(mapper.toDomain(entity2)).thenReturn(restaurant2);

        List<Restaurant> result = adapter.findAll();

        assertEquals(2, result.size());
        assertEquals(List.of(restaurant1, restaurant2), result);

        verify(restaurantJpaRepository).findAll();
    }

    @Test
    @DisplayName("Should delete restaurant by id")
    void shouldDeleteRestaurantById() {

        adapter.delete(1L);

        verify(restaurantJpaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should return true when restaurant exists by id")
    void shouldReturnTrueWhenRestaurantExistsById() {

        when(restaurantJpaRepository.existsById(1L)).thenReturn(true);

        boolean result = adapter.existsById(1L);

        assertTrue(result);

        verify(restaurantJpaRepository).existsById(1L);
    }

    @Test
    @DisplayName("Should return true when restaurant exists by name")
    void shouldReturnTrueWhenRestaurantExistsByName() {

        when(restaurantJpaRepository.existsByName("Pizza")).thenReturn(true);

        boolean result = adapter.existsByName("Pizza");

        assertTrue(result);

        verify(restaurantJpaRepository).existsByName("Pizza");
    }
}