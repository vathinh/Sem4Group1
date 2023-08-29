package com.aptech.group.service.impl;

import com.aptech.group.dto.user.UserCriteria;
import com.aptech.group.dto.user.UserRequest;
import com.aptech.group.dto.user.UserResponse;
import com.aptech.group.dto.user.UserUpdateRequest;
import com.aptech.group.exception.CantDeleteExceptionAnnotation;
import com.aptech.group.exception.NotFoundExceptionAnnotation;
import com.aptech.group.mapstruct.UserMapper;
import com.aptech.group.model.UserEntity;
import com.aptech.group.repository.UserRepository;
import com.aptech.group.service.MessageService;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    private final EntityManager entityManager;

    private final MessageService messageService;
    private static final String DEFAULT_SORT_FIELD = "id";


    @Override
    public void create(UserRequest request) {
        UserEntity userEntity = mapper.toEntity(request);
        userRepository.save(userEntity);
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @NotFoundExceptionAnnotation
    public UserResponse getById(Integer id) {
        return userRepository.findById(id).map(mapper::toResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllByCriteria(UserCriteria userCriteria) {
        Pageable pageable = userCriteria.getPageable();
        List<String> listSort = userCriteria.getSort();
        if (CollectionUtils.isEmpty(listSort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(DEFAULT_SORT_FIELD));
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(listSort.stream().toString()));
        }
        Specification<UserEntity> specification = buildSpecification(userCriteria);
        Page<UserEntity> responses = userRepository.findAll(specification, pageable);
        List<UserResponse> resultPage = responses.stream().map(mapper::toResponse).toList();
        return new PageImpl<>(resultPage, pageable, responses.getTotalElements());
    }

    @Override
    public void updateUser(Integer id, UserUpdateRequest userUpdateRequest) throws NotFoundException {
        UserEntity existed = userRepository.findByIdAndDeleteFlag(id, false).orElseThrow();
        entityManager.detach(existed);
        mapper.updateEntity(userUpdateRequest, existed);
        userRepository.save(existed);
    }

    @Override
    @CantDeleteExceptionAnnotation
    public void deleteUsers(Map<Integer, Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            List<UserEntity> deleteUsers = userRepository.findAllByIds(ids.keySet());
            deleteUsers.forEach(userEntity -> {
                userEntity.setVersion(ids.get(userEntity.getId()));
                userEntity.setDeleteFlag(true);
                entityManager.detach(userEntity);
            });
            userRepository.saveAll(deleteUsers);
        }
    }

    @Override
    @NotFoundExceptionAnnotation
    public List<UserEntity> findAllUsersByTitleIds(List<Integer> ids) {
        return userRepository.findAllByTitleIdIn(ids);
    }

    @Override
    public void updateKeyCloakId(Integer id, String keycloakId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        userEntityOptional.ifPresent(p -> {
            p.setKeycloakId(keycloakId);
            userRepository.save(p);
        });
    }

    private Specification<UserEntity> buildSpecification(UserCriteria userCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isBlank(userCriteria.getFirstName())) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"), userCriteria.getFirstName()));
            }
            if (!StringUtils.isBlank(userCriteria.getSearch())) {
                predicates.add(buildSearchPredicate(criteriaBuilder, root,userCriteria));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Predicate buildSearchPredicate(CriteriaBuilder criteriaBuilder, Root<UserEntity> root, UserCriteria userCriteria) {
        String searchValue = "%" + userCriteria.getSearch() + "%";
        return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), searchValue.toLowerCase()),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), searchValue.toLowerCase())
        );
    }


}
