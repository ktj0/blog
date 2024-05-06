package com.project.blog.controller;

import com.project.blog.dto.SignupRequestDto;
import com.project.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    // 로그인 페이지
    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    // 회원가입
    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        if (!fieldErrorList.isEmpty()) {
            for (FieldError fieldError : fieldErrorList) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }

            return "redirect:/api/user/signup";
        }

        userService.signup(requestDto);

        return "redirect:/api/user/login-page";
    }
}
