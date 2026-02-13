package com.example.backend;

import com.example.backend.model.Product;
import com.example.backend.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductSeeder implements CommandLineRunner {

    ProductService productService;

    public ProductSeeder(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        // Idempotent Seeding
        try {

            Product checkProduct = productService.check(1L);

            if (checkProduct != null) {
                return;
            }

            Product productWhoRemains = new Product(1L, "Ticket", 100);
            // the "He who remains" of the app

            productService.save(productWhoRemains);


        } catch (Exception e) {
            System.out.println("Seeding failed: " + e.getMessage());
        }
    }
}
