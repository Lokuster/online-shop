package com.lokuster.userservice.service;

import com.lokuster.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

}
