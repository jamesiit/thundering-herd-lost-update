package com.example.backend.services;

import com.example.backend.model.Product;
import com.example.backend.repo.ProductRepo;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }


    public void save(Product productWhoRemains) {
        productRepo.save(productWhoRemains);
    }

    public Product check(long id) {
        return productRepo.findById(id).orElse(null);
    }
}
