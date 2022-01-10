package com.example.miniback.controller;

import com.example.miniback.entity.vo.Products;
import com.example.miniback.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Products>> getProductsAll() {
        List<Products> products = productsService.getProductsAll();
        if (products == null || products.size() <= 0) {
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Products>> getProducts(@RequestParam Long productUnum) {
        Optional<Products> products = productsService.getProducts(productUnum);
        if (products == null || products.isEmpty()) {
            return new ResponseEntity<>(products, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(products);
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> addProducts(@RequestBody Products products) {
        Products product = productsService.addProducts(products);
        if (product == null || product.getProductUnum() == null) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> updateProducts(@RequestBody Products products) {
        Products product = productsService.updateProducts(products);
        if (product == null || product.getProductUnum() == null) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ResponseEntity<String> deleteProducts(@RequestParam Long productUnum) {
        Long result = productsService.deleteProducts(productUnum);
        if (result == null || result <= 0) {
            return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("success");
    }
}
