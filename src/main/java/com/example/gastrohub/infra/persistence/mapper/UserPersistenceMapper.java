package com.example.gastrohub.infra.persistence.mapper;

import com.example.gastrohub.domain.user.User;
import com.example.gastrohub.infra.persistence.entity.UserJpaEntity;
import com.example.gastrohub.infra.persistence.role.mapper.RolePersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class UserPersistenceMapper {

    private final RestaurantPersistenceMapper restaurantPersistenceMapper;
    private final RolePersistenceMapper rolePersistenceMapper;

    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .role(rolePersistenceMapper.toEntity(user.getRole()))
                .build();
    }

    public User toDomain(UserJpaEntity entity) {
        return User.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .login(entity.getLogin())
                .password(entity.getPassword())
                .role(rolePersistenceMapper.toDomain(entity.getRole()))
                .restaurants(
                        entity.getRestaurants() == null
                                ? List.of()
                                : entity.getRestaurants()
                                .stream()
                                .map(restaurantPersistenceMapper::toDomain)
                                .toList()
                )
                .build();
    }
}
