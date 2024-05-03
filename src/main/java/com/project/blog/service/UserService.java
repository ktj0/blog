package com.project.blog.service;

import com.project.blog.dto.SignupRequestDto;
import com.project.blog.entity.User;
import com.project.blog.entity.UserRoleEnum;
import com.project.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token}")
    private String ADMIN_TOKEN;

    // 회원가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();
        boolean isAdmin = requestDto.isAdmin();
        String adminToken = requestDto.getAdminToken();
        UserRoleEnum role = UserRoleEnum.USER;

                Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email이 존재합니다.");
        }

        if (isAdmin) {
            if (!ADMIN_TOKEN.equals(adminToken)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }

            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role);

        userRepository.save(user);
    }
}
