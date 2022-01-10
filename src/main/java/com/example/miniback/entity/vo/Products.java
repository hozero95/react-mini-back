package com.example.miniback.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "zero_products")
public class Products {
    @Id
    @Column(name = "product_unum")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_zero_products")
    @SequenceGenerator(name = "seq_zero_products", sequenceName = "seq_zero_products", allocationSize = 1)
    private Long productUnum;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private Long price;

    @Column(name = "regdate")
    private Date regdate;

    @Column(name = "moddate")
    private Date moddate;
}
