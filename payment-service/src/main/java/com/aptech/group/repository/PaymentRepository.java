package com.aptech.group.repository;

import com.aptech.group.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    List<PaymentEntity> findByUserId(String userId);

    Optional<PaymentEntity> findByIdAndUserId(String id, String userId);
}
