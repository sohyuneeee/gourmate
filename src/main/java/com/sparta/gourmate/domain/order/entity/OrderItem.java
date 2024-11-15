package com.sparta.gourmate.domain.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_order_items")
public class OrderItem {  // BaseEntity 상속 (타임스탬프와 생성/수정자 필드 처리)

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // 주문 (FK)

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "menu_id", nullable = false)
    //private Menu menu;  // 메뉴 (FK)

    @Column(nullable = false)
    private int quantity;  // 수량

    @Column(nullable = false)
    private int unitPrice;  // 단가

    @Column(nullable = false)
    private int totalPrice;  // 총 가격

    @PrePersist
    public void prePersist() {
        this.totalPrice = this.unitPrice * this.quantity;  // 총 가격 계산
    }
}
