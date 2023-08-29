package com.aptech.group.service.impl;

import com.aptech.group.dto.account.AccountRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.service.AccountService;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakAdminUrl;

    @Value("${admin.keycloak.client-id}")
    private String keycloakClientId;

    @Value("${admin.keycloak.client-secret}")
    private String keycloakClientSecret;

    @Value("${keycloak.realm}")
    private String keycloakReal;

    @Value("${keycloak.resource}")
    private String keycloakResource;

    private final UserService userService;

    private final MessageServiceImpl messageService;


    private Keycloak getKeycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakAdminUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(keycloakReal)
                .clientId(keycloakClientId)
                .clientSecret(keycloakClientSecret)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
    }

    @Override
    public void createKeycloakUser(AccountRequest accountRequest) throws NotFoundException {
        UserResponse userResponse = userService.getById(accountRequest.getUserId());
        if (userResponse == null) {
           throw new NotFoundException(new NotFoundException("Can not find user"));
        }

        Keycloak keycloak = getKeycloakClient();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userResponse.getEmail());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setEmail(userResponse.getEmail());

        Map<String, List<String>> attributes =new HashMap<>();
        attributes.put("phone", List.of(userResponse.getPhone()));
        attributes.put("userId",  List.of(userResponse.getId().toString()));
        attributes.put("createdBy", List.of(userResponse.getCreatedBy()));
        user.setAttributes(attributes);

        RealmResource realmResource = keycloak.realm(keycloakReal);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);
        int status = response.getStatus();
        if (status == HttpStatus.CONFLICT.value()) {
            throw new RuntimeException("Error on response keycloak");
        }
        String userId = CreatedResponseUtil.getCreatedId(response);
        userService.updateKeyCloakId(accountRequest.getUserId(), userId);

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(accountRequest.getPassword());

        UserResource userResource = usersResource.get(userId);

        // Set password credential
        userResource.resetPassword(passwordCred);
    }

    @Override
    @Transactional
    public void updatePassword(AccountRequest accountRequest) throws NotFoundException {

    }
}
