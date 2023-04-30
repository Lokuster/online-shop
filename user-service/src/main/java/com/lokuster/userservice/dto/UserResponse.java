package com.lokuster.userservice.dto;

import com.lokuster.userservice.model.HasId;
import com.lokuster.userservice.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponse implements HasId {
    private Long id;
    private String username;
    private String email;
    private Double balance;
    private Boolean active;
    private Date createDate;
    private Set<Role> roles = new HashSet<>();
}
