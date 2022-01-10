package com.example.miniback.service;

import com.example.miniback.entity.vo.Products;
import com.example.miniback.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> getProductsAll() {
        return productsRepository.findAll();
    }

    public Optional<Products> getProducts(Long productUnum) {
        return productsRepository.findByProductUnum(productUnum);
    }

    public Products addProducts(Products products) {
        if (!productsRepository.existsByProductName(products.getProductName())) {
            Products product = Products.builder()
                    .productName(products.getProductName())
                    .price(products.getPrice())
                    .regdate(new Date())
                    .build();
            return productsRepository.save(product);
        }
        return null;
    }

    public Products updateProducts(Products products) {
        if (productsRepository.existsByProductUnum(products.getProductUnum())) {
            Products product = Products.builder()
                    .productUnum(products.getProductUnum())
                    .productName(products.getProductName())
                    .price(products.getPrice())
                    .regdate(new Date())
                    .build();
            return productsRepository.save(product);
        }
        return null;
    }

    public Long deleteProducts(Long productUnum) {
        if (productsRepository.existsByProductUnum(productUnum)) {
            return productsRepository.deleteByProductUnum(productUnum);
        }
        return null;
    }
}
