package com.uniquehire.cafe.controller;

import com.uniquehire.cafe.dto.OrderRequestDTO;
import com.uniquehire.cafe.dto.OrderResponseDTO;
import com.uniquehire.cafe.dto.ResponseDTO;
import com.uniquehire.cafe.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseDTO createOrder(@RequestBody OrderRequestDTO request){
        System.out.println("Entered to Controller: OrderController,  method(): createOrder");
        ResponseDTO responseDTO = null;
        if(request != null){
            responseDTO = orderService.createOrder(request);
        }
        return responseDTO;
    }

    @GetMapping("/getOrders")
    public List<OrderResponseDTO> getAllOrders(@RequestParam String tableName){
        List<OrderResponseDTO> responseDTOS =  orderService.getAllOrders();
        return  responseDTOS;
    }

    @GetMapping("/getAllorders/pagination")
    public Page<OrderResponseDTO> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return orderService.getAllOrders(page, size);
    }

    @GetMapping("/getOrders/{id}")
    public OrderResponseDTO getOrderByID(@PathVariable Long id){
        OrderResponseDTO response = orderService.getOrderByID(id);
        return  response;
    }

    @GetMapping("/getOrders/by")
    public OrderResponseDTO getOrderBy(@RequestParam String createdBy){
        OrderResponseDTO response = orderService.getOrderBy(createdBy);
        return  response;
    }
}
