package com.aptech.group.controller;

import com.aptech.group.dto.user.UserCriteria;
import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.dto.user.UserUpdateRequest;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.NotFoundException;
import java.util.Map;

import static com.aptech.group.controller.UserServiceEndpoints.USER_SERVICE_PATH;

@RequestMapping(USER_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserServiceController {

    private final UserService userService;

    @PostMapping("registration")
    public void create(@RequestBody UserRequest userRequest) {
        userService.create(userRequest);
    }

    @GetMapping("getByCriteria")
    @PreAuthorize("hasAnyRole(@environment.getProperty('employee.user.read'), @environment.getProperty('employee.user.full'))")
    public Page<UserResponse> getAllByCriteria(UserCriteria userCriteria) {
        return userService.getAllByCriteria(userCriteria);
    }

    @GetMapping("customer/{email}")
    @PreAuthorize("hasAnyRole(@environment.getProperty('customer.user.read'), @environment.getProperty('customer.user.full'))")
    public UserResponse getCustomerByEmail(@PathVariable("email") String email) {
        return userService.getCustomerByEmail(email);
    }

    @GetMapping("employee/{email}")
    @PreAuthorize("hasAnyRole(@environment.getProperty('employee.user.read'), @environment.getProperty('employee.user.full'))")
    public UserResponse getUserByEmail(@PathVariable("email") String email) {
        return userService.getByEmail(email);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole(@environment.getProperty('employee.user.update'), @environment.getProperty('employee.user.full'))")
    public void update(Integer id, UserUpdateRequest userUpdateRequest) throws NotFoundException {
        userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole(@environment.getProperty('employee.user.delete'), @environment.getProperty('employee.user.full'))")
    public void deleteUsers(Map<Integer, Long> ids) {
        userService.deleteUsers(ids);
    }
}
