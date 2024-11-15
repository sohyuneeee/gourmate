package com.sparta.gourmate.domain.menu.service;

import com.sparta.gourmate.domain.menu.dto.MenuRequestDto;
import com.sparta.gourmate.domain.menu.dto.MenuResponseDto;
import com.sparta.gourmate.domain.menu.dto.MenuUpdateRequestDto;
import com.sparta.gourmate.domain.menu.entity.Menu;
import com.sparta.gourmate.domain.menu.entity.MenuStatusEnum;
import com.sparta.gourmate.domain.menu.repository.MenuRepository;
import com.sparta.gourmate.domain.store.entity.Store;
import com.sparta.gourmate.domain.store.repository.StoreRepository;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public MenuResponseDto createMenu(User user, MenuRequestDto requestDto) {
        Store store = checkStore(requestDto.getStoreId());
        checkUserByStore(user, store);
        checkRole(user);
        Menu menu = menuRepository.save(new Menu(requestDto, store, user));
        return new MenuResponseDto(menu);
    }

    public MenuResponseDto updateMenu(User user, UUID menuId, MenuUpdateRequestDto updateRequestDto) {
        Menu menu = checkMenu(menuId);
        checkUserByMenu(user, menu);
        menu.update(updateRequestDto);
        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public MenuResponseDto getMenu(User user, UUID menuId) {
        Menu menu = checkMenu(menuId);
        checkUserByMenu(user, menu);
        return new MenuResponseDto(menu);
    }

    @Transactional(readOnly = true)
    public Page<MenuResponseDto> getMenuList(UUID storeId, String query, int page, int size, String sortBy, boolean isAsc) {
        Store store = checkStore(storeId);
        Pageable pageable = createPageableWithSorting(page, size, sortBy, isAsc);
        List<MenuStatusEnum> statusList = Arrays.asList(MenuStatusEnum.AVAILABLE, MenuStatusEnum.OUT_OF_STOCK);
        Page<Menu> menuList;
        if (query != null && !query.isEmpty()) {
            String queryWithWildcard = "%" + query + "%";
            menuList = menuRepository.findActiveMenuByStoreAndStatusAndName(storeId, statusList, queryWithWildcard, pageable);
        } else {
            menuList = menuRepository.findActiveMenuByStoreAndStatus(storeId, statusList, pageable);
        }
        return menuList.map(MenuResponseDto::new);
    }

    public void deleteMenu(User user, UUID menuId) {
        Menu menu = checkMenu(menuId);
        checkUserByMenu(user, menu);
        menu.delete(user.getId());
    }

    private Pageable createPageableWithSorting(int page, int size, String sortBy, boolean isAsc) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page, size, sort);
    }

    private Menu checkMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
    }

    private Store checkStore(UUID storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
    }

    private void checkUserByStore(User user, Store store) {
        if (!Objects.equals(store.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_SAME);
        }
    }

    private void checkUserByMenu(User user, Menu menu) {
        if (!Objects.equals(menu.getUser().getId(), user.getId())) {
            throw new CustomException(ErrorCode.USER_NOT_SAME);
        }
    }

    private void checkRole(User user) {
        UserRoleEnum role = UserRoleEnum.OWNER;
        if (!Objects.equals(user.getRole(), role)) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }

}
