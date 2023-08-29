package com.aptech.group.mapstruct;

import com.aptech.group.model.UserEntity;
import com.aptech.group.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityMapper {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    public UserEntity mappingUser(Integer userId) {
        if (userId == null) {
            return null;
        }
        Optional<UserEntity> userEntityMono = userRepository.findById(userId);
        return userEntityMono.orElse(null);
    }



}
