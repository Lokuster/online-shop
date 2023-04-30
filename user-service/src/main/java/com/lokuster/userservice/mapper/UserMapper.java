package com.lokuster.userservice.mapper;

import com.lokuster.userservice.dto.UserRequest;
import com.lokuster.userservice.dto.UserResponse;
import com.lokuster.userservice.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromRequest(UserRequest userRequest);

    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> users);
}
