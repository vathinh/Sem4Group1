package com.aptech.group.controller;

import com.aptech.group.dto.OrderRequest;
import com.aptech.group.dto.OrderResponse;
import com.aptech.group.model.OrderEntity;
import com.aptech.group.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aptech.group.controller.OrderServiceEndpoints.ORDER_SERVICE_PATH;

@RequestMapping(ORDER_SERVICE_PATH)
@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderServiceController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(
            @RequestParam(name = "status", required = false) List<String> status,
            @RequestAttribute String userId,
            @RequestAttribute Boolean isAdmin
    ) {
        return orderService.getAll(status, userId, isAdmin);
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(
            @RequestBody OrderRequest orderRequest,
            @RequestAttribute String userId
    ) {
        return orderService.createOrder(orderRequest, userId);
    }

    @PutMapping(path = "{orderId}")
    public ResponseEntity<OrderEntity> updateOrder(
            @PathVariable("orderId") String id,
            @RequestBody OrderRequest orderRequest,
            @RequestAttribute String userId
    ) {
        return orderService.updateOrder(id, orderRequest, userId);
    }

    //delete order
    @DeleteMapping(path = "{orderId}")
    public ResponseEntity deleteOrder(@PathVariable("orderId") String id) {
        return orderService.deleteOrder(id);
    }
}
