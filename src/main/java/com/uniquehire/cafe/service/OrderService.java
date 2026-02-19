package com.uniquehire.cafe.service;

import com.uniquehire.cafe.dto.OrderRequestDTO;
import com.uniquehire.cafe.dto.OrderResponseDTO;
import com.uniquehire.cafe.dto.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    public ResponseDTO createOrder(OrderRequestDTO request);
    public List<OrderResponseDTO> getAllOrders();
    public OrderResponseDTO getOrderByID(Long id);

    OrderResponseDTO getOrderBy(String createdBy);

    public Page<OrderResponseDTO> getAllOrders(int page, int size);
}
