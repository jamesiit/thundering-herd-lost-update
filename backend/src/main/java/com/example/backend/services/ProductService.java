package com.example.backend.services;

import com.example.backend.model.Product;
import com.example.backend.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //service methods

    @Transactional
    public void processProduct(int clientQuantity) {

        // getting the product

        Product product = productRepo.findByIdAndLock(1L);

        // business logic

        if (product.getProdQuantity() <= 0) {

            throw new IllegalStateException();

        }

        // order processing wait ()
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }

        // save product
        product.setProdQuantity( product.getProdQuantity() - clientQuantity);
        productRepo.save(product);

    }

    // polling logic
    public int getCurrentQuantity() {
        Product product = productRepo.findById(1L).orElseThrow();

        return product.getProdQuantity();

    }

}

