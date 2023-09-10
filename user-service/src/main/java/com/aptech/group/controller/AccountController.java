package com.aptech.group.controller;

import com.aptech.group.dto.account.AccountResponse;
import com.aptech.group.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

import static com.aptech.group.controller.UserServiceEndpoints.ACCOUNT_PATH;

@RestController
@Slf4j
@Validated
@RequestMapping(ACCOUNT_PATH)
@RequiredArgsConstructor
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public Page<AccountResponse> getAllAccounts(@QueryParam("search") String search, @QueryParam("first") Integer first, @QueryParam("max") Integer max, @QueryParam("briefRepresentation") boolean briefRepresentation) {
        return accountService.getAllAccounts(search, first, max, briefRepresentation);
    }
}
