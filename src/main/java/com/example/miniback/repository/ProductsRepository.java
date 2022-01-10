package com.example.miniback.repository;

import com.example.miniback.entity.vo.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    List<Products> findAll();

    Optional<Products> findByProductUnum(Long productUnum);

    Long deleteByProductUnum(Long productUnum);

    boolean existsByProductUnum(Long productUnum);

    boolean existsByProductName(String productName);
}
