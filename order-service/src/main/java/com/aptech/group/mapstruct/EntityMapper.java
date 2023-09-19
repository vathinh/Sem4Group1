package com.aptech.group.mapstruct;

import com.aptech.group.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceContext;

public class EntityMapper {
    @PersistenceContext
    private EntityMapper entityMapper;

    @Autowired
    OrderRepository orderRepository;
}
