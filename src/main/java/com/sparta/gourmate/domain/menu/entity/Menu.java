package com.sparta.gourmate.domain.menu.entity;

import com.sparta.gourmate.domain.menu.dto.MenuRequestDto;
import com.sparta.gourmate.domain.menu.dto.MenuUpdateRequestDto;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_menu")
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MenuStatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Menu(MenuRequestDto requestDto, Store store, User user) {
        this.name = requestDto.getMenuName();
        this.description = requestDto.getDescription();
        this.price = requestDto.getPrice();
        this.status = requestDto.getStatus();
        this.store = store;
        this.user = user;
    }

    public void update(MenuUpdateRequestDto updateRequestDto) {
        updateName(updateRequestDto.getMenuName());
        updateDescription(updateRequestDto.getDescription());
        updatePrice(updateRequestDto.getPrice());
        updateStatus(updateRequestDto.getStatus());
    }

    private void updateStatus(MenuStatusEnum status) {
        if (status != null) {
            this.status = status;
        }
    }

    private void updatePrice(Integer price) {
        if (price != null) {
            this.price = price;
        }
    }

    private void updateDescription(String description) {
        if (StringUtils.hasText(description)) {
            this.description = description;
        }
    }

    private void updateName(String menuName) {
        if (StringUtils.hasText(menuName)) {
            this.name = menuName;
        }
    }

}
