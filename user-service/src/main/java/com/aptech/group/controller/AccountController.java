package com.aptech.group.controller;

import com.aptech.group.dto.account.AccountRequest;
import com.aptech.group.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import static com.aptech.group.controller.UserServiceEndpoints.ACCOUNT_PATH;

@RequestMapping(ACCOUNT_PATH)
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
public class AccountController {


    private AccountService accountService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole(@environment.getProperty('account.create'), @environment.getProperty('account.full'))")
    public void create(@Valid @RequestBody AccountRequest accountRequest)
            throws NotFoundException {
        accountService.createKeycloakUser(accountRequest);
    }
}
