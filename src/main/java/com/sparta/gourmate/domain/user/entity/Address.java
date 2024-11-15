package com.sparta.gourmate.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_addresses")
public class Address  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // 주소 ID, PK

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id", nullable = false)
    //private User user;  // 사용자 정보, FK

    @Column(nullable = false)
    private String address;  // 실제 주소

    @Column
    private String addressRequest;  // 요청 사항 (예: 배송 시 특정 요청)


//    // 생성자
//    //public Address(User user, String address, String addressRequest) {
//        this.user = user;
//        this.address = address;
//        this.addressRequest = addressRequest;
//    }

    // 주소 정보 수정
    public void updateAddress(String address, String addressRequest) {
        this.address = address;
        this.addressRequest = addressRequest;
    }
}
