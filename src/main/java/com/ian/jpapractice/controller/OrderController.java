package com.ian.jpapractice.controller;

import com.ian.jpapractice.domain.Order;
import com.ian.jpapractice.domain.User;
import com.ian.jpapractice.domain.product.Product;
import com.ian.jpapractice.dto.OrderDto;
import com.ian.jpapractice.dto.OrderSearchDto;
import com.ian.jpapractice.repository.OrderRepository;
import com.ian.jpapractice.service.OrderService;
import com.ian.jpapractice.service.ProductService;
import com.ian.jpapractice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping("/create")
    public String create(Model model) {
        List<User> users = userService.findAll();
        List<Product> products = productService.findAll();

        model.addAttribute("users", users);
        model.addAttribute("products", products);

        return "orders/orderForm";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute OrderDto orderDto) {
        orderService.createOrder(orderDto.getUserId(), orderDto.getProductId(), orderDto.getCount());

        return "redirect:/orders";
    }

    @GetMapping
    public String getAllOrders(OrderSearchDto orderSearchDto, Model model) {
        List<Order> orders = orderRepository.findAll(orderSearchDto);

        model.addAttribute("orderSearch", orderSearchDto);
        model.addAttribute("orders", orders);

        return "orders/orderList";
    }
}
