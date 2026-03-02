package com.example.backend.sqs;

import com.example.backend.dto.OrderDTO;
import com.example.backend.model.Order;
import com.example.backend.model.OrderStatus;
import com.example.backend.repo.OrderRepo;
import com.example.backend.repo.ProductRepo;
import com.example.backend.state.StoreState;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class ProductConsumer {

    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final StoreState storeState;

    public ProductConsumer(ProductRepo productRepo, OrderRepo orderRepo, StoreState storeState) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.storeState = storeState;
    }

    @SqsListener("${my.sqs.queue.name}")
    public void processFromQueue(OrderDTO orderDTO) {

        System.out.println("Processing order for: " + orderDTO.getUserId());

        //simulate wait, like credit card check
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }

        //get the quantity
        int count = productRepo.decrementProduct(orderDTO.getClientQuantity(), 1L);

        //create the order
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setClientTime(orderDTO.getClientTime());
        order.setClientQuantity(orderDTO.getClientQuantity());

        if (count > 0) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            System.out.println("Success!");
        } else {
            order.setOrderStatus(OrderStatus.FAILED);
            System.out.println("Failed! Sold out!");
            storeState.markAsSoldOut();
        }

        //save the order
        orderRepo.save(order);

    }


}
