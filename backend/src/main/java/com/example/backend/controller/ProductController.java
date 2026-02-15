package com.example.backend.controller;

import com.example.backend.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/count")
@CrossOrigin("http://localhost:5173/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getCurrentQuantity() {

        int quantity = productService.getCurrentQuantity();

        return new ResponseEntity<>(quantity, HttpStatus.OK);

    }

}
