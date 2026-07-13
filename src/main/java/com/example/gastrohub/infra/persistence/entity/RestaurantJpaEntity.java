package com.example.gastrohub.infra.persistence.entity;

import com.example.gastrohub.domain.restaurant.enums.CuisineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RestaurantJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type", nullable = false, length = 50)
    private CuisineType cuisineType;
    @Column(name = "opening_hours", nullable = false, length = 100)
    private String openingHours;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    value = ConstraintMode.CONSTRAINT,
                    name = "user_fk"
            )
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserJpaEntity user;

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<MenuItemJpaEntity> menuItems = new ArrayList<>();

}
