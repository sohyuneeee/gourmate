package com.sparta.gourmate.domain.menu.controller;

import com.sparta.gourmate.domain.menu.dto.MenuRequestDto;
import com.sparta.gourmate.domain.menu.dto.MenuResponseDto;
import com.sparta.gourmate.domain.menu.dto.MenuUpdateRequestDto;
import com.sparta.gourmate.domain.menu.service.MenuService;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping()
    public ResponseEntity<MenuResponseDto> createMenu(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @Valid @RequestBody MenuRequestDto requestDto) {
        User user = userDetails.getUser();
        MenuResponseDto responseDto = menuService.createMenu(user, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> updateMenu(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                      @PathVariable UUID menuId,
                                                      @Valid @RequestBody MenuUpdateRequestDto updateRequestDto) {
        User user = userDetails.getUser();
        MenuResponseDto responseDto = menuService.updateMenu(user, menuId, updateRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                   @PathVariable UUID menuId) {
        User user = userDetails.getUser();
        MenuResponseDto responseDto = menuService.getMenu(user, menuId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<Page<MenuResponseDto>> getMenuList(@PathVariable UUID storeId,
                                                             @RequestParam String query,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "createdAt") String sortBy,
                                                             @RequestParam(defaultValue = "false") boolean isAsc) {
        Page<MenuResponseDto> responseDtoPage = menuService.getMenuList(storeId, query, page-1, size, sortBy, isAsc);
        return ResponseEntity.ok(responseDtoPage);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable UUID menuId) {
        User user = userDetails.getUser();
        menuService.deleteMenu(user, menuId);
        return ResponseEntity.ok().build();
    }

}
