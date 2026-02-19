package com.example.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product_tbl")
public class Product {

    @Id
    @Column(name = "prod_id")
    private long prodId;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "prod_quantity")
    private int prodQuantity;

    @Version
    private Integer version;

    public Product(long prodId, String prodName, int prodQuantity) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.prodQuantity = prodQuantity;
    }

    public int getProdQuantity() {
        return prodQuantity;
    }

    public void setProdQuantity(int prodQuantity) {
        this.prodQuantity = prodQuantity;
    }

    public Product() {
    }
}
