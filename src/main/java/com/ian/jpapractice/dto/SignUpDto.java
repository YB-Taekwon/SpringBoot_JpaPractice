package com.ian.jpapractice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
