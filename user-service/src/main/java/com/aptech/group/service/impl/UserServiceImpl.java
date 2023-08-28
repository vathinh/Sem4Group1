package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.mapstruct.UserMapper;
import com.aptech.group.model.UserEntity;
import com.aptech.group.repository.UserRepository;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private final UserMapper mapper;
    @Override
    public void create(UserRequest request) {
        UserEntity userEntity = mapper.toEntity(request);
        userRepository.save(userEntity);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(mapper::toResponse).toList();
    }
}
