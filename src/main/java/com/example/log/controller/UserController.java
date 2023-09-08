package com.example.log.controller;

import com.example.log.domain.*;
import com.example.log.service.UserService;
import com.example.log.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        String encodedPassword = encoder.encode(userJoinRequest.getPassword());
        User user = new User(userJoinRequest.getUserAccount(), encodedPassword);
        userService.join(user);
        UserJoinResponse userJoinResponse = new UserJoinResponse(user.getUserAccount());

        return Response.success(userJoinResponse);
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        Map<String, String> token = userService.login(userLoginRequest.getUserAccount(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token.toString()));
    }

    @PostMapping("/hello")
    public String hello(@RequestBody UserLoginRequest userLoginRequest, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return "안녕";
        }
        return "실패";
    }

    @PostMapping("/refresh")
    public Response<RefreshResponse> refreshAccessToken(@RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        // 리프레쉬 토큰 검증
        if (!JwtTokenUtil.isRefreshTokenValid(refreshToken, secretKey)) {
            return Response.error("Invalid refresh token");
        }

        // 리프레쉬 토큰으로부터 어세스 토큰 생성
        String newAccessToken = JwtTokenUtil.generateAccessTokenFromRefreshToken(refreshToken, secretKey);

        RefreshResponse refreshResponse = new RefreshResponse(newAccessToken);
        return Response.success(refreshResponse);
    }
}