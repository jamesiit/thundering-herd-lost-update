package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;
import com.example.backend.services.OrderService;
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

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // for testing purposes, to check if endpoint works
    @GetMapping("/")
    public ResponseEntity<?> sayHello() {
        return new ResponseEntity<>("Hi", HttpStatus.OK);
    }


    @PostMapping("/")
    public ResponseEntity<?> handleOrder(@RequestBody OrderDTO orderDTO) {

        try {

            orderService.createOrder(orderDTO);

            //for testing purposes. There's more functionality to be added here!

            Map<String, String> response = new HashMap<>();

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
