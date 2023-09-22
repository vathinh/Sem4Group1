package com.aptech.group.service;

import com.aptech.group.dto.OrderRequest;
import com.aptech.group.dto.OrderResponse;
import com.aptech.group.model.OrderEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<List<OrderResponse>> getAll(List<String> status, String userId, Boolean isAdmin);
    ResponseEntity<OrderEntity> createOrder(OrderRequest request, String userId);
    ResponseEntity<OrderEntity> updateOrder(String orderId, OrderRequest request, String userId);
    ResponseEntity deleteOrder(String orderId);
}
