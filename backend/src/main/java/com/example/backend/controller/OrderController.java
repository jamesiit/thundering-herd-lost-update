package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;

import com.example.backend.state.StoreState;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin("http://localhost:5173/")
public class OrderController {

    private final SqsTemplate sqsTemplate;
    private final String queueName;
    private final StoreState storeState;

    public OrderController(SqsTemplate sqsTemplate, @Value("${my.sqs.queue.name}") String queueName, StoreState storeState) {
        this.sqsTemplate = sqsTemplate;
        this.queueName = queueName;
        this.storeState = storeState;
    }

    @PostMapping("/")
    public ResponseEntity<?> handleOrder(@RequestBody OrderDTO orderDTO) {

        Map<String, HttpStatus> response = new HashMap<>();

        try {

            if (storeState.checkIsSoldOut()) {
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            //this will serialize and attach the payload
            sqsTemplate.send(queueName, orderDTO);

            System.out.println("Sent order: " + orderDTO.getUserId() + " to queue!");

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        } catch (Exception e) {

            System.out.println("Something went wrong!");

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


        }


    }

}
