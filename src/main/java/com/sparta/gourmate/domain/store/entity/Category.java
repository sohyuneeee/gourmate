package com.sparta.gourmate.domain.store.entity;

import com.sparta.gourmate.domain.store.dto.CategoryRequestDto;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "p_categories")
@NoArgsConstructor
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Store> storeList;

    public Category(CategoryRequestDto requestDto) {
        this.name = requestDto.getName();
    }

    public void update(CategoryRequestDto requestDto) {
        this.name = requestDto.getName();
    }
}