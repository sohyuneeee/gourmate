package com.sparta.gourmate.domain.store.entity;

import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.store.dto.StoreRequestDto;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "p_stores")
@NoArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column
    private double averageRating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "store")
    private List<Menu> menuList = new ArrayList<>();

    public Store(StoreRequestDto requestDto, User user, Category category) {
        this.name = requestDto.getName();
        this.location = requestDto.getLocation();
        this.category = category;
        this.user = user;
    }

    public void update(StoreRequestDto requestDto, Category category) {
        this.category = category;
        this.name = requestDto.getName();
        this.location = requestDto.getLocation();
    }

    public void updateAvg(double avg) {
        this.averageRating = avg;
    }
}
