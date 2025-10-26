package com.ian.jpapractice.domain.product;

import com.ian.jpapractice.domain.Category;
import com.ian.jpapractice.domain.OrderProduct;
import com.ian.jpapractice.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;
    private int price;
    private int stock;

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProductList = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Category> categories = new ArrayList<>();

    /**
     * 재고 관리 메서드
     */
    // 재고 증가
    public void addStock(int stock) {
        this.stock += stock;
    }

    // 재고 감소
    public void removeStock(int stock) {
        int restStock = this.stock - stock;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }
}
