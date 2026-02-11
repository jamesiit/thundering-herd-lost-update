package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_tbl")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private long prodId;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "prod_quantity")
    private int prodQuantity;

    public int getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public Product() {
    }
}
