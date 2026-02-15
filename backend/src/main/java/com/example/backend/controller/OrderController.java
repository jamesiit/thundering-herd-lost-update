package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;

import com.example.backend.model.Order;
import com.example.backend.model.OrderStatus;
import com.example.backend.model.Product;
import com.example.backend.services.OrderService;
import com.example.backend.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:5173/")
public class OrderController {

    private OrderService orderService;
    private ProductService productService;


    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<?> handleOrder(@RequestBody OrderDTO orderDTO) {

        Map<String, HttpStatus> response = new HashMap<>();

        try {

            Order createdOrder = orderService.createOrder(orderDTO);

            Product product = productService.checkQuantity();

            int getQuantity = product.getProdQuantity();

            if (getQuantity <= 0) {

                createdOrder.setOrderStatus(OrderStatus.FAILED);

                orderService.updateOrderStatus(createdOrder);

                return new ResponseEntity<>(response, HttpStatus.CONFLICT);

            }

            //simulate the order processing wait in real systems like a credit card check
            Thread.sleep(20);

            productService.decrementQuantity(orderDTO.getClientQuantity());

            createdOrder.setOrderStatus(OrderStatus.COMPLETED);

            orderService.updateOrderStatus(createdOrder);

            return new ResponseEntity<>(response, HttpStatus.OK);


        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }
}
