package com.sparta.gourmate.domain.user.controller;

import com.sparta.gourmate.domain.user.dto.SignupRequestDto;
import com.sparta.gourmate.domain.user.dto.UserResponseDto;
import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.service.UserService;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody SignupRequestDto requestDto) {
        UserResponseDto responseDto = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public Page<UserResponseDto> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "false") boolean isAsc
    ) {
        return userService.getUsers(page - 1, size, sortBy, isAsc);
    }

    @PutMapping("/{userId}")
    public UserResponseDto updateUser(@PathVariable Long userId, @Valid @RequestBody SignupRequestDto requestDto) {
        return userService.updateUser(userId, requestDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long userId) {
        User tokenUser = userDetails.getUser();
        userService.deleteUser(tokenUser, userId);
    }
}
