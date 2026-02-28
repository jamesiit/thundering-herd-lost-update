package com.example.backend.controller;

import com.example.backend.dto.OrderDTO;

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

    public OrderController(SqsTemplate sqsTemplate, @Value("${my.sqs.queue.name}") String queueName) {
        this.sqsTemplate = sqsTemplate;
        this.queueName = queueName;
    }

    @PostMapping("/")
    public ResponseEntity<?> handleOrder(@RequestBody OrderDTO orderDTO) {

        Map<String, HttpStatus> response = new HashMap<>();

        try {

           sqsTemplate.send(queueName, orderDTO);

            System.out.println("Sent message to queue!");

           return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        }  catch (Exception e) {

            System.out.println("Something went wrong!");
            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);



        }


    }

}
