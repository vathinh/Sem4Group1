package com.aptech.group.service;

import com.aptech.group.dto.account.AccountRequest;

import javax.ws.rs.NotFoundException;

public interface AccountService {
    void createKeycloakUser(AccountRequest accountRequest)
            throws NotFoundException;

    void updatePassword(AccountRequest accountRequest)
            throws NotFoundException;

}
