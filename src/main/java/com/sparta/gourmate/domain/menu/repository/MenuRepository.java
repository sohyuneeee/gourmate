package com.sparta.gourmate.domain.menu.repository;

import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.menu.entity.MenuStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    @Query("select m from Menu m " +
            "where m.store.id = :storeId " +
            "and m.status in :statusList " +
            "and m.isDeleted = false")
    Page<Menu> findActiveMenuByStoreAndStatus(UUID storeId, List<MenuStatusEnum> statusList, Pageable pageable);

    @Query("select m from Menu m " +
            "where m.store.id = :storeId " +
            "and m.status in :statusList " +
            "and m.name LIKE :query " +
            "and m.isDeleted = false")
    Page<Menu> findActiveMenuByStoreAndStatusAndName(UUID storeId, List<MenuStatusEnum> statusList, String query, Pageable pageable);

}
