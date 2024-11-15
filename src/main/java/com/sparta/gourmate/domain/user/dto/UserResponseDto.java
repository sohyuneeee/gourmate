package com.sparta.gourmate.domain.user.dto;

import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.domain.user.entity.UserRoleEnum;
import com.sparta.gourmate.global.common.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto extends BaseDto {
    private Long id;
    private String username;
    private String email;
    private UserRoleEnum role;

    public UserResponseDto(User user) {
        super(user);
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
