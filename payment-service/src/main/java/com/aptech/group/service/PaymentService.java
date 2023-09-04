package com.aptech.group.service;

import com.aptech.group.dto.PaymentRequest;
import com.aptech.group.model.PaymentEntity;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PaymentService {
    public ResponseEntity<List<PaymentEntity>> getPayments(String userId);

    public ResponseEntity<PaymentEntity> getSinglePayment(String id, String userId);

    public ResponseEntity<PaymentEntity> createPayment(PaymentRequest request, String userId);

    public ResponseEntity<PaymentEntity> updatePayment(Long paymentId, PaymentRequest request, String userId);

    public ResponseEntity deletePayment(Long paymentId);
}
