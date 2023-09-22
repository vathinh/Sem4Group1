package com.aptech.group.service.impl;

import com.aptech.group.dto.OrderRequest;
import com.aptech.group.dto.OrderResponse;
import com.aptech.group.dto.ProductResponse;
import com.aptech.group.mapstruct.OrderMapper;
import com.aptech.group.model.OrderEntity;
import com.aptech.group.model.OrderProductEntity;
import com.aptech.group.model.OrderStatus;
import com.aptech.group.repository.OrderRepository;
import com.aptech.group.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public static boolean containStatus(String input) {
        for (OrderStatus o : OrderStatus.values()) {
            if (o.name().equals(input)) {
                return true;
            }
        }
        return false;
    }
    private final OrderMapper mapper;

    @Transient
    public BigDecimal getTotalOrderPrice(List<OrderProductEntity> orderProducts) {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (OrderProductEntity op : orderProducts) {
            sum = sum.add(op.getPrice().multiply(BigDecimal.valueOf(op.getQuantity())));
        }
        return sum;
    }

    public void checkExistProducts(OrderRequest request) {
        //request product => list product id
        List<String> idList = request.getProducts().stream()
                .map(OrderProductEntity::getId)
                .toList();

        //get products by id
        ProductResponse[] productResponseArray = webClientBuilder.build().get()
                .uri("http://product/api/v1/product",
                        uriBuilder -> uriBuilder.queryParam("id", idList).build())
                .retrieve()
                .bodyToMono(ProductResponse[].class)
                .block();

        //check quantity
        Arrays.stream(productResponseArray).forEach(product -> {
            OrderProductEntity productInStock = (OrderProductEntity) request.getProducts().stream()
                    .filter(p -> p.getId().equals(product.getId()) && product.getQuantity() >= p.getQuantity())
                    .findFirst()
                    .orElse(null);

            if(productInStock == null) {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        });
    }

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
        checkExistProducts(request);

        OrderEntity orderEntity = OrderEntity.builder()
                .status(OrderStatus.PENDING.name())
                .total(getTotalOrderPrice(request.getProducts()))
                .products(request.getProducts())
                .createdBy(userId)
                .lastModifiedBy(userId)
                .build();
        return new ResponseEntity<>(orderRepository.save(orderEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderEntity> updateOrder(String orderId, OrderRequest request, String userId) {
        Optional<OrderEntity> orderData = orderRepository.findById(orderId);
        if(orderData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(!containStatus(request.getStatus())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(request.getStatus().equalsIgnoreCase(OrderStatus.PAID.name())) {
            //check exist products
            checkExistProducts(request);
        }

        //update
        if(request.getStatus() != null) { orderData.get().setStatus(request.getStatus()); }
        if(request.getProducts() != null) {
            orderData.get().setTotal(getTotalOrderPrice(request.getProducts()));
            orderData.get().setProducts(request.getProducts());
        }
        orderData.get().setLastModifiedBy(userId);
        return new ResponseEntity<>(orderRepository.save(orderData.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteOrder(String orderId) {
        Optional<OrderEntity> orderData = orderRepository.findById(orderId);
        if (orderData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderRepository.deleteById(orderId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
