package com.ian.jpapractice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ian.jpapractice.domain.DeliveryStatus.COMP;
import static com.ian.jpapractice.domain.OrderStatus.CANCEL;
import static com.ian.jpapractice.domain.OrderStatus.ORDER;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    public void addUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    /**
     * 양방향 연결 메서드
     */
    // Order <-> OrderProduct 양뱡향 연결
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    // Order <-> Delivery 양방향 연결
    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 주문 생성 팩토리 메서드
     */
    public static Order createOrder(User user, Delivery delivery, OrderProduct... orderProducts) {
        Order order = new Order();
        order.setUser(user);
        order.setDelivery(delivery);
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
        }
        order.setStatus(ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /**
     * 비즈니스 로직
     */
    // 주문 취소
    public void cancelOrder() {
        if (delivery.getStatus() == COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(CANCEL);
        for (OrderProduct orderProduct : orderProducts) {
            orderProduct.cancel();
        }
    }

    // 총 주문 금액 조회
    public int getTotalPrice() {
        return orderProducts.stream()
                .mapToInt(OrderProduct::getTotalPrice)
                .sum();
    }
}
