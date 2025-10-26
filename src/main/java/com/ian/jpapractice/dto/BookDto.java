package com.ian.jpapractice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private Long id;

    private String name;
    private int price;
    private int stock;

    private String author;
    private String isbn;
}
