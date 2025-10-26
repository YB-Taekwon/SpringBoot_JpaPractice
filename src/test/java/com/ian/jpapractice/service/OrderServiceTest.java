package com.ian.jpapractice.service;

import com.ian.jpapractice.domain.Address;
import com.ian.jpapractice.domain.Order;
import com.ian.jpapractice.domain.User;
import com.ian.jpapractice.domain.product.Book;
import com.ian.jpapractice.exception.NotEnoughStockException;
import com.ian.jpapractice.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.ian.jpapractice.domain.OrderStatus.CANCEL;
import static com.ian.jpapractice.domain.OrderStatus.ORDER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    User user;
    Book book;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("test");
        user.setAddress(new Address("city", "street", "zipcode"));
        em.persist(user);

        book = new Book();
        book.setName("book");
        book.setAuthor("author");
        book.setIsbn("isbn");
        book.setPrice(10000);
        book.setStock(10);
        em.persist(book);
    }

    @Test
    @DisplayName("주문 생성 성공")
    void create_order_success_test() {
        // given
        int orderCount = 2;

        // when
        Long orderId = orderService.createOrder(user.getId(), book.getId(), orderCount);
        Order order = orderRepository.findById(orderId);

        // then
        assertThat(order.getStatus()).isEqualTo(ORDER);
        assertThat(order.getOrderProducts().size()).isEqualTo(1);
        assertThat(order.getTotalPrice()).isEqualTo(10000 * orderCount);
        assertThat(book.getStock()).isEqualTo(8);
    }

    @Test
    @DisplayName("주문 생성 중 재고 수량 초과 시, 예외가 발생해야 한다.")
    void create_order_fail_stock_over_test() {
        // given
        int orderCount = 12;

        // when & then
        assertThatThrownBy(() -> orderService.createOrder(user.getId(), book.getId(), orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    @DisplayName("주문 취소 성공")
    void cancel_order_success_test() {
        // given
        int orderCount = 2;
        Long orderId = orderService.createOrder(user.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);
        Order order = orderRepository.findById(orderId);

        // then
        assertThat(order.getStatus()).isEqualTo(CANCEL);
        assertThat(book.getStock()).isEqualTo(10);
    }
}