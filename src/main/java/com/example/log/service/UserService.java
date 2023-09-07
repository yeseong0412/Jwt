package com.example.log.service;


import com.example.log.domain.User;
import com.example.log.exception.AppException;
import com.example.log.exception.ErrorCode;
import com.example.log.repository.UserRepository;
import com.example.log.token.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Transactional
    public User join(User user) {
        userRepository.findByUserAccount(user.getUserAccount())
                .ifPresent(user1 -> {
                    throw new AppException(ErrorCode.DUPLICATED_USER_NAME);
                });
        userRepository.save(user);

        return user;
    }

    public String login(String userAccount, String password) {
        User user = userRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
        if (!encoder.matches(password, user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }

        return JwtTokenUtil.createToken(userAccount, secretKey);

    }

    public User getUserByUserAccount(String userAccount) {
        return userRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUNDED));
    }
}
