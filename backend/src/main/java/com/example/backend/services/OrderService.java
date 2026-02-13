package com.example.backend.services;

import com.example.backend.dto.OrderDTO;
import com.example.backend.model.Order;
import com.example.backend.model.OrderStatus;
import com.example.backend.repo.OrderRepo;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void createOrder(OrderDTO orderDTO) {

        Order createdOrder = new Order();

        createdOrder.setUserId(orderDTO.getUserId());
        createdOrder.setOrderStatus(OrderStatus.PENDING);
        createdOrder.setClientQuantity(orderDTO.getClientQuantity());
        createdOrder.setClientTime(orderDTO.getClientTime());

        orderRepo.save(createdOrder);

    }
}
