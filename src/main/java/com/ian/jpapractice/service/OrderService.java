package com.ian.jpapractice.service;

import com.ian.jpapractice.domain.Delivery;
import com.ian.jpapractice.domain.Order;
import com.ian.jpapractice.domain.OrderProduct;
import com.ian.jpapractice.domain.User;
import com.ian.jpapractice.domain.product.Product;
import com.ian.jpapractice.repository.OrderRepository;
import com.ian.jpapractice.repository.ProductRepository;
import com.ian.jpapractice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public Long createOrder(Long userId, Long productId, int count) {
        // 1. 회원 / 상품 조회
        User user = userRepository.findById(userId);
        Product product = productRepository.findById(productId);

        // 2. 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());

        // 3. 주문 상품 생성
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), count);

        // 4. 주문 생성
        Order order = Order.createOrder(user, delivery, orderProduct);

        // 5. 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    public void cancelOrder(Long orderId) {
        // 1. 주문 조회
        Order order = orderRepository.findById(orderId);

        // 2. 주문 취소
        order.cancelOrder();
    }
}
