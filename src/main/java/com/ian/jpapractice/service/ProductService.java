package com.ian.jpapractice.service;

import com.ian.jpapractice.domain.product.Book;
import com.ian.jpapractice.domain.product.Product;
import com.ian.jpapractice.dto.BookDto;
import com.ian.jpapractice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Book editBook(Long productId, BookDto bookDto) {
        Book book = (Book) productRepository.findById(productId);
        book.setName(bookDto.getName());
        book.setPrice(bookDto.getPrice());
        book.setStock(bookDto.getStock());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());

        return book;
    }
}
