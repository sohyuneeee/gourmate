package com.sparta.gourmate.domain.order.entity;

import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_orders")
public class Order extends BaseEntity {  // BaseEntity 상속

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // 주문 ID, PK

    @Column(nullable = false, length = 255)
    private String address;  // 배송지

    @Column(nullable = false, length = 255)
    private String orderRequest;  // 요청 사항

    @Column(nullable = false, length = 20)
    private String orderType;  // 주문 유형 (ONLINE, OFFLINE)

    @Column(nullable = false, length = 20)
    private String orderStatus;  // 주문 상태 (PENDING, CONFIRMED, CANCELLED)

    @Column(nullable = false)
    private int totalPrice;  // 총 가격
}
