package com.project.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Size(min = 1, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$")
    @NotBlank
    private String username;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;

    private boolean admin = false;

    private String adminToken = "";
}
