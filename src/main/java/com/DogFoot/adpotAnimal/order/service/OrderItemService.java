package com.DogFoot.adpotAnimal.order.service;

import com.DogFoot.adpotAnimal.order.dto.OrderItemRequest;
import com.DogFoot.adpotAnimal.order.entity.OrderItem;
import com.DogFoot.adpotAnimal.order.repository.OrderItemRepository;
import com.DogFoot.adpotAnimal.products.entity.Product;
import com.DogFoot.adpotAnimal.products.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    // 주문 상품 추가
    public OrderItem create(Product product, int orderPrice, int count) {
        return orderItemRepository.save(OrderItem.createOrderItem(product, orderPrice, count));
    }

    // 주문 상품 조회
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not Found OrderItem Id: " + id));
    }

    @Transactional
    public List<Long> createOrderItems(List<OrderItemRequest> requests) {
        List<Long> orderItemIds = new ArrayList<>();

        for (OrderItemRequest request : requests) {
            try {
                Product product = productService.findProductById(request.getProductId());

                OrderItem createdOrderItem = OrderItem.createOrderItem(product, request.getOrderPrice(), request.getCount());
                OrderItem savedOrderItem = orderItemRepository.save(createdOrderItem);
                orderItemIds.add(savedOrderItem.getId());

                product.removeStock(request.getCount());
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("상품이 존재하지 않습니다: " + request.getProductId());
            }
        }

        return orderItemIds;
    }

    // 주문 상품 취소
    public void deleteById(Long id) {
        findById(id);
        orderItemRepository.deleteById(id);
    }

}
