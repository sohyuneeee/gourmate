package com.sparta.gourmate.global.common;

import com.sparta.gourmate.domain.user.entity.User;
import com.sparta.gourmate.global.exception.CustomException;
import com.sparta.gourmate.global.exception.ErrorCode;
import com.sparta.gourmate.global.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class CheckRoleAop {

    @Pointcut("execution(* com.sparta.gourmate.domain.user.controller.UserController.*(..))")
    private void userController() {
    }

    @Before("userController()")
    public void execute(JoinPoint joinPoint) {
        // 파라미터에 @AuthenticationPrincipal로 UserDetails를 받으면 해당 인자 사용, 안받으면 가져오기
        UserDetailsImpl userDetails = Arrays.stream(joinPoint.getArgs())
                .filter(UserDetailsImpl.class::isInstance)
                .map(UserDetailsImpl.class::cast)
                .findFirst()
                .orElse(null);
        if (userDetails == null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                return;
            }
            userDetails = (UserDetailsImpl) auth.getPrincipal();
        }

        User loginUser = userDetails.getUser();

        // admin 계정은 넘어가기
        if (loginUser.getRole().isAdmin()) {
            return;
        }

        // 일반 계정은 args userId가 토큰 userId랑 같은지 체크
        Long userId = Arrays.stream(joinPoint.getArgs())
                .filter(Long.class::isInstance)
                .map(Long.class::cast)
                .findFirst()
                .orElse(null);
        if (!Objects.equals(userId, loginUser.getId())) {
            throw new CustomException(ErrorCode.AUTH_AUTHORIZATION_FAILED);
        }
    }
}
