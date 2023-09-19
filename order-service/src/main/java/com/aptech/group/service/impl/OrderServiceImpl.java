package com.aptech.group.service.impl;

import com.aptech.group.dto.OrderRequest;
import com.aptech.group.dto.OrderResponse;
import com.aptech.group.mapstruct.OrderMapper;
import com.aptech.group.model.OrderEntity;
import com.aptech.group.repository.OrderRepository;
import com.aptech.group.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;

    private final OrderMapper mapper;

    @Override
    public ResponseEntity<List<OrderResponse>> getAll(List<String> status, String userId, Boolean isAdmin) {
        if(isAdmin) {
            return ResponseEntity.ok(orderRepository.findAll().stream().map(mapper::toResponse).toList());
        }
        if(status!= null) {
            return ResponseEntity.ok(orderRepository.findByStatusInAndCreatedByOrderByLastModifiedDateDesc(status, userId).stream().map(mapper::toResponse).toList());
        }
        return ResponseEntity.ok(orderRepository.findByCreatedByOrderByLastModifiedDateDesc(userId).stream().map(mapper::toResponse).toList());
    }

    @Override
    public ResponseEntity<OrderEntity> createOrder(OrderRequest request, String userId) {
        OrderEntity orderEntity = mapper.toEntity(request);
        return new ResponseEntity<>(orderRepository.save(orderEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderEntity> updateOrder(String userId, String orderId, OrderRequest request) {
        Optional<OrderEntity> orderData = orderRepository.findById(orderId);
        return new ResponseEntity<>(orderRepository.save(orderData.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
