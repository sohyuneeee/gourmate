package com.sparta.gourmate.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "이름은 필수값입니다.")
    @Size(min = 4, max = 10, message = "USERNAME_POLICY")
    @Pattern(regexp = "^[a-z0-9]+$", message = "USERNAME_POLICY")
    private String username;

    @NotBlank(message = "비밀번호는 필수값입니다.")
    @Size(min = 8, max = 15, message = "PASSWORD_POLICY")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*)(+=._-])[a-zA-Z\\d!@#$%^&*)(+=._-]+$", message = "PASSWORD_POLICY")
    private String password;

    @NotBlank(message = "이메일은 필수값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    private Boolean isOwner = false;

    private Boolean isAdmin = false;

    private String adminToken = "";
}
