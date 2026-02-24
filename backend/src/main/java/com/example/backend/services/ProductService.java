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

    //seeder methods
    public void save(Product productWhoRemains) {
        productRepo.save(productWhoRemains);
    }

    public Product check(long id) {
        return productRepo.findById(id).orElse(null);
    }

    //service method

    public void processProduct(int clientQuantity) {

        // order processing wait first (like credit card check)
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }

        int count = productRepo.decrementProduct(clientQuantity, 1L);

        if (count == 0) {
            throw new IllegalStateException();
        }

    }

    // polling logic
    public int getCurrentQuantity() {
        Product product = productRepo.findById(1L).orElseThrow();

        return product.getProdQuantity();

    }

}

