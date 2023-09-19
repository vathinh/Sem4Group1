package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserCriteria;
import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.dto.user.UserUpdateRequest;
import com.aptech.group.exception.InvalidPermissionException;
import com.aptech.group.exception.ResourceNotFoundException;
import com.aptech.group.mapstruct.UserMapper;
import com.aptech.group.model.UserEntity;
import com.aptech.group.repository.UserRepository;
import com.aptech.group.service.AccountService;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    private final EntityManager entityManager;

    private final AccountService accountService;
    private static final String DEFAULT_SORT_FIELD = "id";

    private ResourceNotFoundException resourceNotFoundException() {
        return new ResourceNotFoundException("No user found with the given email");
    }

    private InvalidPermissionException invalidPermissionException() {
        return new InvalidPermissionException("You cant access to other profile");
    }


    @Override
    @Transactional
    public void create(UserRequest request) {
        request.setKeycloakId(accountService.createKeycloak(request));
        UserEntity userEntity = mapper.toEntity(request);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public UserResponse getById(Integer id) {
        return userRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(this::resourceNotFoundException);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllByCriteria(UserCriteria userCriteria) {
        Specification<UserEntity> specification = buildSpecification(userCriteria);
        Page<UserEntity> responses = userRepository.findAll(specification, getPageable(userCriteria));
        List<UserResponse> resultPage = responses.stream().map(mapper::toResponse).toList();
        return new PageImpl<>(resultPage, getPageable(userCriteria), responses.getTotalElements());
    }

    private Pageable getPageable(UserCriteria userCriteria) {
        Pageable pageable = userCriteria.getPageable();
        List<String> listSort = userCriteria.getSort();
        if (CollectionUtils.isEmpty(listSort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(DEFAULT_SORT_FIELD));
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(listSort.stream().toString()));
        }
        return pageable;
    }

    @Override
    @Transactional
    public void updateUser(Integer id, UserUpdateRequest userUpdateRequest) throws NotFoundException {
        UserEntity existed = userRepository.findByIdAndDeleteFlag(id, false).orElseThrow();
        entityManager.detach(existed);
        mapper.updateEntity(userUpdateRequest, existed);
        userRepository.save(existed);
    }

    @Override
    @Transactional
    public void deleteUsers(Map<Integer, Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<UserEntity> deleteUsers = userRepository.findAllById(ids.keySet());
            deleteUsers.forEach(userEntity -> {
                userEntity.setVersion(ids.get(userEntity.getId()));
                userEntity.setDeleteFlag(true);
                entityManager.detach(userEntity);
            });
            userRepository.saveAll(deleteUsers);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsersByTitleIds(List<Integer> ids) {
        return userRepository.findAllByTitleIdIn(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCustomerByEmail(String keycloakId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .filter(keycloakId::equals)
                .map(userKeycloakId -> userRepository.findByKeycloakId(keycloakId).orElseThrow(this::resourceNotFoundException))
                .map(mapper::toResponse)
                .orElseThrow(this::invalidPermissionException);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email).orElseThrow(this::resourceNotFoundException);
        return mapper.toResponse(entity);
    }


    private Specification<UserEntity> buildSpecification(UserCriteria userCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = Stream.of(
                            buildEqualPredicate(criteriaBuilder, root, "firstName", userCriteria.getFirstName()),
                            buildEqualPredicate(criteriaBuilder, root, "userType", userCriteria.getUserType()),
                            buildSearchPredicate(criteriaBuilder, root, userCriteria)
                    )
                    .filter(Objects::nonNull)
                    .toList();

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate buildEqualPredicate(CriteriaBuilder criteriaBuilder, Root<UserEntity> root, String field, String value) {
        if (!StringUtils.isBlank(value)) {
            return criteriaBuilder.equal(root.get(field), value);
        }
        return null;
    }

    private Predicate buildSearchPredicate(CriteriaBuilder criteriaBuilder, Root<UserEntity> root, UserCriteria userCriteria) {
        if (!StringUtils.isBlank(userCriteria.getSearch())) {
            String searchValue = "%" + userCriteria.getSearch() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), searchValue.toLowerCase()),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), searchValue.toLowerCase())
            );
        }
        return null;
    }



}
