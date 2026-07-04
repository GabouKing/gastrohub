package com.example.gastrohub.infra.persistence.adapter;

import com.example.gastrohub.domain.menuitem.MenuItem;
import com.example.gastrohub.domain.menuitem.MenuItemGateway;
import com.example.gastrohub.infra.persistence.mapper.MenuItemPersistenceMapper;
import com.example.gastrohub.infra.persistence.repository.MenuItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MenuItemRepositoryAdapter implements MenuItemGateway {
    private final MenuItemJpaRepository repository;
    private final MenuItemPersistenceMapper mapper;

    @Override
    public MenuItem save(MenuItem menuItem) {
        var entity = mapper.toEntity(menuItem);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<MenuItem> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MenuItem> findByRestaurantId(Long restaurantId) {
        return repository.findByRestaurantId(restaurantId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
