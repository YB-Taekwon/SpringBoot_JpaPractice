package com.ian.jpapractice.dto;

import com.ian.jpapractice.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {
    private String userName;
    private OrderStatus orderStatus;
}
