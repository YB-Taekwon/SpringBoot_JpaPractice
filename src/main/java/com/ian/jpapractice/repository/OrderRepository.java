package com.ian.jpapractice.repository;

import com.ian.jpapractice.domain.Order;
import com.ian.jpapractice.domain.OrderStatus;
import com.ian.jpapractice.domain.QOrder;
import com.ian.jpapractice.domain.QUser;
import com.ian.jpapractice.dto.OrderSearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final JPAQueryFactory query;
    private final EntityManager em;
    private static final QOrder order = QOrder.order;
    private static final QUser user = QUser.user;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findById(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearchDto cond) {
        return query
                .select(order)
                .from(order)
                .join(order.user, user)
                .where(
                        statusEq(cond.getOrderStatus()),
                        nameLike(cond.getUserName())
                )
                .limit(1000)
                .fetch();
    }

    private BooleanExpression statusEq(OrderStatus statusCond) {
        return statusCond == null ? null : order.status.eq(statusCond);
    }

    private BooleanExpression nameLike(String nameCond) {
        if (!StringUtils.hasText(nameCond)) return null;

        return user.name.like("%" + nameCond + "%");
    }
}
