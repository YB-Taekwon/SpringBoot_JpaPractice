package com.ian.jpapractice.controller;

import com.ian.jpapractice.domain.Address;
import com.ian.jpapractice.domain.User;
import com.ian.jpapractice.dto.SignUpDto;
import com.ian.jpapractice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signUpDto", new SignUpDto());
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute SignUpDto signUpDto, BindingResult result) {
        if (result.hasErrors()) {
            return "users/signup";
        }

        Address address = new Address(signUpDto.getCity(), signUpDto.getStreet(), signUpDto.getZipcode());

        User user = new User();
        user.setName(signUpDto.getName());
        user.setAddress(address);

        userService.signUp(user);
        return "redirect:/";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/userList";
    }

}
