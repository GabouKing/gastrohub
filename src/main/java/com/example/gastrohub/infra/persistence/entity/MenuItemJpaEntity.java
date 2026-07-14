package com.example.gastrohub.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
public class MenuItemJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "restaurant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_menu_items_restaurant")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RestaurantJpaEntity restaurant;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Boolean availableOnlyOnRestaurant;
    private String photoPath;

    public MenuItemJpaEntity(
            Long id,
            Long restaurantId,
            String name,
            String description,
            BigDecimal price,
            Boolean availableOnlyOnRestaurant,
            String photoPath
    ) {
        this.id = id;
        setRestaurantId(restaurantId);
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyOnRestaurant = availableOnlyOnRestaurant;
        this.photoPath = photoPath;
    }

    public Long getRestaurantId() {
        return restaurant == null ? null : restaurant.getId();
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurant = restaurantId == null
                ? null
                : RestaurantJpaEntity.builder()
                .id(restaurantId)
                .build();
    }
}
