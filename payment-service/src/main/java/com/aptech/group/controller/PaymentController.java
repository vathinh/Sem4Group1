package com.aptech.group.controller;

import com.aptech.group.dto.PaymentRequest;
import com.aptech.group.model.PaymentEntity;
import com.aptech.group.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/payment")
public record PaymentController(PaymentService paymentService) {
    @GetMapping
    public ResponseEntity<List<PaymentEntity>> getProducts(@RequestAttribute String userId) {
        return paymentService.getPayments(userId);
    }

    //get single payment
    @GetMapping(path = "{paymentId}")
    public ResponseEntity<PaymentEntity> getSinglePayment(
            @PathVariable("paymentId") String id,
            @RequestAttribute String userId
    ) {
        return paymentService.getSinglePayment(id, userId);
    }

    //create payment
    @PostMapping
    public ResponseEntity<PaymentEntity> createPayment(
            @RequestBody PaymentRequest paymentRequest,
            @RequestAttribute String userId
    ) {
        return paymentService.createPayment(paymentRequest, userId);
    }

    //update payment
    @PutMapping(path = "{paymentId}")
    public ResponseEntity<PaymentEntity> updateProduct(
            @PathVariable("paymentId") Long id,
            @RequestBody PaymentRequest paymentRequest,
            @RequestAttribute String userId
    ) {
        return paymentService.updatePayment(id, paymentRequest, userId);
    }

    //delete payment
    @DeleteMapping(path = "{paymentId}")
    public ResponseEntity deletePayment(@PathVariable("paymentId") Long id) {
        return paymentService.deletePayment(id);
    }
}
