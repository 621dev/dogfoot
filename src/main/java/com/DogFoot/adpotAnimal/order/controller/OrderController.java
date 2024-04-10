package com.DogFoot.adpotAnimal.order.controller;

import com.DogFoot.adpotAnimal.order.dto.OrderRequest;
import com.DogFoot.adpotAnimal.order.dto.OrderResponse;
import com.DogFoot.adpotAnimal.order.entity.Delivery;
import com.DogFoot.adpotAnimal.order.entity.Order;
import com.DogFoot.adpotAnimal.order.entity.OrderItem;
import com.DogFoot.adpotAnimal.order.service.DeliveryService;
import com.DogFoot.adpotAnimal.order.service.OrderItemService;
import com.DogFoot.adpotAnimal.order.service.OrderService;
import com.DogFoot.adpotAnimal.users.entity.CustomUserDetails;
import com.DogFoot.adpotAnimal.users.entity.Users;
import com.DogFoot.adpotAnimal.users.service.CustomUserDetailsService;
import com.DogFoot.adpotAnimal.users.service.UsersService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final DeliveryService deliveryService;
    private final UsersService usersService;


    // 주문 생성
    @PostMapping("")
    public ResponseEntity<Long> addOrder(@RequestBody OrderRequest request) {
        Long orderId = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    // 주문 목록 검색
    @GetMapping("")
    public ResponseEntity<List<OrderResponse>> findOrders(@RequestParam Long usersId) {

        PageRequest pageable = PageRequest.of(0, 10, Sort.by(String.valueOf(usersId)).ascending());
        Page<Order> ordersPage = orderService.findAllByUsersId(usersId, pageable);

        List<OrderResponse> orderResponses = ordersPage.getContent()
            .stream()
            .map(OrderResponse::new)
            .toList();

        return ResponseEntity.ok().body(orderResponses);
    }

    // 특정 주문 검색
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findOrder(@PathVariable(value = "id") Long id) {
        Order order = orderService.findById(id);

        return ResponseEntity.ok().body(new OrderResponse(order));
    }


}
