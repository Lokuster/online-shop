package com.lokuster.userservice.controller;

import com.lokuster.userservice.dto.UserRequest;
import com.lokuster.userservice.dto.UserResponse;
import com.lokuster.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RegistrationController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    public static final String REST_URL = "/api/v1/register";
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
        log.info("registerUser {}", userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(userRequest));
    }
}
