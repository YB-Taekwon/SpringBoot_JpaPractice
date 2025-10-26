package com.ian.jpapractice.controller;

import com.ian.jpapractice.domain.product.Book;
import com.ian.jpapractice.dto.BookDto;
import com.ian.jpapractice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/create")
    public String createBookForm(Model model) {
        model.addAttribute("bookDto", new BookDto());
        return "/products/createBook";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute BookDto bookDto) {

        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setPrice(bookDto.getPrice());
        book.setStock(bookDto.getStock());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());

        productService.save(book);

        return "redirect:/";
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "/products/productList";
    }

    @GetMapping("/{productId}/edit")
    public String editBookForm(@PathVariable Long productId, Model model) {
        Book book = (Book) productService.findById(productId);
        model.addAttribute("book", book);
        return "/products/editProduct";
    }

    @PostMapping("/{productId}/edit")
    public String editBook(@PathVariable Long productId, @ModelAttribute BookDto bookDto, Model model) {
        productService.editBook(productId, bookDto);
        return "redirect:/products";
    }
}
