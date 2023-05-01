package com.lokuster.userservice.dto;

import com.lokuster.userservice.model.HasId;
import com.lokuster.userservice.model.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserRequest implements HasId {
    @Nullable
    private Long id;
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^(?!\\$2a\\$).*$", message = "Password cannot start with '$2a$'")
    private String password;

    @Nullable
    private Set<Role> roles = new HashSet<>();
}
