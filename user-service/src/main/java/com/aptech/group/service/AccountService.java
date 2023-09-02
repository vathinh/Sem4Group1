package com.aptech.group.service;

import com.aptech.group.dto.account.AccountCriteria;
import com.aptech.group.dto.account.AccountRequest;
import com.aptech.group.dto.account.AccountResponse;
import com.aptech.group.dto.user.UserRequest;
import org.springframework.data.domain.Page;

import javax.ws.rs.NotFoundException;
import java.util.List;

public interface AccountService {
    void updatePassword(AccountRequest accountRequest)
            throws NotFoundException;

    String createKeycloak(UserRequest userRequest);

    Page<AccountResponse> getAllAccounts(String search, Integer first, Integer max, boolean briefRepresentation);

}
