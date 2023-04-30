package com.lokuster.userservice.service;

import com.lokuster.userservice.dto.UserRequest;
import com.lokuster.userservice.dto.UserResponse;
import com.lokuster.userservice.error.IllegalRequestDataException;
import com.lokuster.userservice.mapper.UserMapper;
import com.lokuster.userservice.model.User;
import com.lokuster.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.lokuster.userservice.util.ValidationUtil.assureIdConsistent;
import static com.lokuster.userservice.util.ValidationUtil.checkNew;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserResponse> getAllUsers() {
        return mapper.toResponse(repository.findAll());
    }

    public UserResponse getUserById(Long id) {
        return mapper.toResponse(repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id=" + id + " not found")));
    }

    public UserResponse createUser(UserRequest userRequest) {
        checkNew(userRequest);
        User userToSave = mapper.fromRequest(userRequest);
        prepareToSave(userToSave);
        return mapper.toResponse(repository.save(userToSave));
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        assureIdConsistent(userRequest, id);
        User userToSave = mapper.fromRequest(userRequest);
        prepareToSave(userToSave);
        return mapper.toResponse(repository.save(userToSave));
    }

    public void deleteUser(Long id) {
        int count = repository.delete(id);
        if (count == 0) {
            throw new IllegalRequestDataException("User with id=" + id + " not found");
        }
    }

    private void prepareToSave(User user) {
        if (user.getBalance() == null) {
            user.setBalance(0D);
        }
        if (user.getActive() == null) {
            user.setActive(true);
        }
        if (user.getCreateDate() == null) {
            user.setCreateDate(new Date());
        }
    }
}
