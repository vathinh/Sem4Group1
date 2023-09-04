package com.aptech.group.service.impl;

import com.aptech.group.dto.PaymentRequest;
import com.aptech.group.dto.PaymentResponse;
import com.aptech.group.model.PaymentEntity;
import com.aptech.group.repository.PaymentRepository;
import com.aptech.group.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<List<PaymentEntity>> getPayments(String userId) {
        return ResponseEntity.ok(paymentRepository.findByUserId(userId));
    }

    @Override
    public ResponseEntity<PaymentEntity> getSinglePayment(String id, String userId) {
        Optional<PaymentEntity> paymentData = paymentRepository.findByIdAndUserId(id, userId);
        if(paymentData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PaymentEntity>(paymentData.get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentEntity> createPayment(PaymentRequest request, String userId) {
        PaymentEntity data = PaymentEntity.builder()
                .name(request.getName())
                .type(request.getType())
                .balance(request.getBalance())
                .userId(userId)
                .build();

        return new ResponseEntity<PaymentEntity>(paymentRepository.save(data), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentEntity> updatePayment(Long paymentId, PaymentRequest request, String userId) {
        Optional<PaymentEntity> data = paymentRepository.findById(paymentId);
        if(data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(request.getName() != null) { data.get().setName(request.getName()); }
        if(request.getType() != null) { data.get().setType(request.getType()); }
        if(request.getBalance() != null) { data.get().setBalance(request.getBalance()); }

        return new ResponseEntity<PaymentEntity>(paymentRepository.save(data.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deletePayment(Long paymentId) {
        Optional<PaymentEntity> data = paymentRepository.findById(paymentId);
        if(data.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        paymentRepository.deleteById(paymentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
