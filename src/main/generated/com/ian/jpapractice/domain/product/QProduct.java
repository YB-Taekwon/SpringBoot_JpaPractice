package com.ian.jpapractice.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 101074979L;

    public static final QProduct product = new QProduct("product");

    public final ListPath<com.ian.jpapractice.domain.Category, com.ian.jpapractice.domain.QCategory> categories = this.<com.ian.jpapractice.domain.Category, com.ian.jpapractice.domain.QCategory>createList("categories", com.ian.jpapractice.domain.Category.class, com.ian.jpapractice.domain.QCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<com.ian.jpapractice.domain.OrderProduct, com.ian.jpapractice.domain.QOrderProduct> orderProductList = this.<com.ian.jpapractice.domain.OrderProduct, com.ian.jpapractice.domain.QOrderProduct>createList("orderProductList", com.ian.jpapractice.domain.OrderProduct.class, com.ian.jpapractice.domain.QOrderProduct.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

