package com.aptech.group.controller;

import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aptech.group.controller.UserServiceEndpoints.USER_SERVICE_PATH;

@RequestMapping(USER_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;
    @GetMapping
    @PreAuthorize("hasAnyRole(@environment.getProperty('user.read'), @environment.getProperty('user.full'))")
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole(@environment.getProperty('user.create'), @environment.getProperty('user.full'))")
    public void create(@RequestBody UserRequest userRequest) {
        userService.create(userRequest);
    }
}
