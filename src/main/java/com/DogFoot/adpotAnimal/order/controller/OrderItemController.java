package com.DogFoot.adpotAnimal.order.controller;

import com.DogFoot.adpotAnimal.order.dto.OrderItemRequest;
import com.DogFoot.adpotAnimal.order.dto.OrderItemResponse;
import com.DogFoot.adpotAnimal.order.entity.OrderItem;
import com.DogFoot.adpotAnimal.order.service.OrderItemService;
import com.DogFoot.adpotAnimal.products.entity.Product;
import com.DogFoot.adpotAnimal.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final ProductService productService;

    @PostMapping("/orderItem")
    public ResponseEntity<Long> createOrderItem (@RequestBody OrderItemRequest request) {
        Product product = productService.findProductById(request.getProductId());

        OrderItem createdOrderItems = orderItemService.create(product, product.getProduct_price(), request.getCount());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItems.getId());
    }

    @PostMapping("/orderItems")
    public ResponseEntity<List<Long>> createOrderItem (@RequestBody List<OrderItemRequest> requests) {
        List<Long> orderItemId = new ArrayList<>();

        for (OrderItemRequest request : requests) {
            Product product = productService.findProductById(request.getProductId());
            OrderItem createdOrderItems = orderItemService.create(product, product.getProduct_price(), request.getCount());
            orderItemId.add(createdOrderItems.getId());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemId);
    }

    @GetMapping("/orderItem/{id}")
    public ResponseEntity<OrderItemResponse> getOrderDetail(@PathVariable(value = "id") long id) {
        OrderItem orderItem = orderItemService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new OrderItemResponse(orderItem));
    }

    @DeleteMapping("/orderItem/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable(value = "id") long id) {
        orderItemService.deleteById(id);

        return ResponseEntity.ok().build();
    }


}
