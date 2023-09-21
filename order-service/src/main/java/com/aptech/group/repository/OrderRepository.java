package com.aptech.group.repository;

import com.aptech.group.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    List<OrderEntity> findByCreatedByOrderByLastModifiedDateDesc(String createdBy);

    List<OrderEntity> findByStatusInAndCreatedByOrderByLastModifiedDateDesc(List<String> status, String createdBy);
}
