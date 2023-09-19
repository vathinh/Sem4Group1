package com.aptech.group.service.impl;

import com.aptech.group.dto.title.TitleCriteria;
import com.aptech.group.dto.title.TitleRequest;
import com.aptech.group.dto.title.TitleResponse;
import com.aptech.group.dto.title.UpdateTitleRequest;
import com.aptech.group.mapstruct.TitleMapper;
import com.aptech.group.model.TitleEntity;
import com.aptech.group.model.UserEntity;
import com.aptech.group.repository.TitleRepository;
import com.aptech.group.service.TitleService;
import com.aptech.group.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    private final TitleMapper mapper;

    private final EntityManager entityManager;

    private final UserService userService;
    private static final String DEFAULT_SORT_FIELD = "id";

    @Override
    @Transactional(readOnly = true)
    public TitleResponse getTitleById(Integer id) {
        return mapper.toResponse(titleRepository.getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TitleResponse> getAllTitles(TitleCriteria titleCriteria) {
        Pageable pageable = titleCriteria.getPageable();
        List<String> listSort = titleCriteria.getSort();
        if (CollectionUtils.isEmpty(listSort)) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(DEFAULT_SORT_FIELD));
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(listSort.stream().toString()));
        }
        Specification<TitleEntity> specification = buildSpecification(titleCriteria);
        Page<TitleEntity> responses = titleRepository.findAll(specification, pageable);
        List<TitleResponse> resultPage = responses.stream().map(mapper::toResponse).toList();
        return new PageImpl<>(resultPage, pageable, responses.getTotalElements());
    }

    private Specification<TitleEntity> buildSpecification(TitleCriteria titleCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isBlank(titleCriteria.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name"), titleCriteria.getName()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    @Override
    @Transactional
    public void createTitle(TitleRequest titleRequest) {
        titleRepository.save(mapper.toEntity(titleRequest));
    }

    @Override
    @Transactional
    public void updateTitle(Integer id, UpdateTitleRequest updateTitleRequest) {
        Optional<TitleEntity> optionalTitleEntity = titleRepository.findById(id);
        TitleEntity updateTitleEntity = mapper.toEntity(updateTitleRequest);

        if (optionalTitleEntity.isPresent()) {
            TitleEntity titleEntity = optionalTitleEntity.get();
            titleEntity.setName(updateTitleEntity.getName());
            titleEntity.setCode(updateTitleEntity.getCode());
            titleEntity.setVersion(updateTitleEntity.getVersion());

            entityManager.detach(titleEntity);
            titleRepository.save(titleEntity);
        }
    }

    @Override
    @Transactional
    public void deleteTitles(Map<Integer, Long> ids) throws BadRequestException {
        if (!CollectionUtils.isEmpty(ids)) {
            List<UserEntity> userUse = userService.findAllUsersByTitleIds(
                    ids.keySet().stream().toList());
            if (!userUse.isEmpty()) {
                Set<String> cannotDeleteTitles = userUse.stream()
                        .map(employee -> employee.getTitle().getCode()).collect(
                                Collectors.toSet());
                throw new NotFoundException("User not found");
            }

            List<TitleEntity> deleteTitles = titleRepository.findAllById(ids.keySet());
            deleteTitles.forEach(item -> {
                item.setVersion(ids.get(item.getId()));
                item.setDeleteFlag(true);
                entityManager.detach(item);
            });

            titleRepository.saveAll(deleteTitles);
        }
    }
}
