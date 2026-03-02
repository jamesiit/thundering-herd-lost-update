package com.example.backend.services;

import com.example.backend.model.Order;
import com.example.backend.repo.OrderRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Optional <Order> check(String userId)  {
        return orderRepo.findByUserId(userId);
    }

}
