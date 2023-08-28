package com.aptech.group.service;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.model.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {
    void create(UserRequest request);
    List<UserResponse> getAll();
}
