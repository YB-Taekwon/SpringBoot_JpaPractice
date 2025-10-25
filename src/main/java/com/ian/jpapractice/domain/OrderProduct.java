package com.ian.jpapractice.domain;

import com.ian.jpapractice.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_product")
@Getter
@Setter
public class OrderProduct {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int orderPrice;
    private int count;

    /**
     * 주문 상품 생성 팩토리 메서드
     */
    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderPrice(orderPrice);
        orderProduct.setCount(count);

        product.removeStock(count);

        return orderProduct;
    }

    /**
     * 비즈니스 로직
     */
    // 재고 수량 복구
    public void cancel() {
        getProduct().addStock(count);
    }

    // 총 주문 금액 조회
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
