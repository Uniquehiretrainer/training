package com.uniquehire.cafe.serviceimpl;

import com.uniquehire.cafe.dto.OrderDetailsDTO;
import com.uniquehire.cafe.dto.OrderRequestDTO;
import com.uniquehire.cafe.dto.OrderResponseDTO;
import com.uniquehire.cafe.dto.ResponseDTO;
import com.uniquehire.cafe.model.AuditLog;
import com.uniquehire.cafe.model.Order;
import com.uniquehire.cafe.model.OrderDetails;
import com.uniquehire.cafe.repository.AuditLogRepository;
import com.uniquehire.cafe.repository.OrderRepository;
import com.uniquehire.cafe.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    public OrderServiceImpl(OrderRepository orderRepository, AuditLogRepository auditLogRepository){
        this.orderRepository = orderRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public ResponseDTO createOrder(OrderRequestDTO request) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Order order = new Order();
            List<OrderDetails> orderDetails = new ArrayList<>();

            if(Objects.nonNull(request.getOrderNumber())){
                order.setOrderNumber(request.getOrderNumber());
            }

            if(Objects.nonNull(request.getTableName())){
                order.setTableName(request.getTableName());
            }

            if(Objects.nonNull(request.getCreatedBy())){
                order.setCreatedBy(request.getCreatedBy());
                order.setUpdatedBy(request.getCreatedBy());
            }
            order.setCreatedAt(LocalDateTime.now());
            order.setUpdatedAt(LocalDateTime.now());

            List<OrderDetailsDTO> orderDetailsDTOList = request.getOrderDetails();
            for(OrderDetailsDTO detailsDTO : orderDetailsDTOList){
                OrderDetails orderDetail = new OrderDetails();
                if(Objects.nonNull(detailsDTO.getName())){
                    orderDetail.setName(detailsDTO.getName());
                }
                if(Objects.nonNull(detailsDTO.getPrice())){
                    orderDetail.setPrice(detailsDTO.getPrice());
                }
                if(Objects.nonNull(detailsDTO.getType())){
                    orderDetail.setType(detailsDTO.getType());
                }
                if(Objects.nonNull(detailsDTO.getQuantity())){
                    orderDetail.setQuantity(detailsDTO.getQuantity());
                }
                if(Objects.nonNull(detailsDTO.getComments())){
                    orderDetail.setComments(detailsDTO.getComments());
                }
                if(Objects.nonNull(detailsDTO.getCreatedBy())){
                    orderDetail.setCreatedBy(detailsDTO.getCreatedBy());
                    orderDetail.setUpdatedBy(detailsDTO.getCreatedBy());
                }
                orderDetail.setCreatedAt(LocalDateTime.now());
                orderDetail.setUpdatedAt(LocalDateTime.now());
                orderDetail.setOrder(order);
                orderDetails.add(orderDetail);
            }
            order.setOrderDetails(orderDetails);
            Order orderResponse = orderRepository.save(order);
            if(orderResponse != null && orderResponse.getId() != null){
                responseDTO.setMessage("OrderCreated Successfully");
                responseDTO.setStatus(HttpStatus.CREATED.value());
            }
            AuditLog auditLog = new AuditLog();
            auditLog.setId(order.getId());
            auditLog.setTableName("Orders");
            auditLogRepository.save(auditLog);

        }catch (Exception e){
            responseDTO.setMessage("OrderCreated Failure");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            e.printStackTrace();
        }
        return responseDTO;
    }

    @Override
    public Page<OrderResponseDTO> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(this::mapToOrderResponseDTO);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .toList(); // use Collectors.toList() if Java < 16
    }

    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setTableName(order.getTableName());
        dto.setCreatedBy(order.getCreatedBy());

        List<OrderDetailsDTO> details = order.getOrderDetails() == null
                ? List.of()
                : order.getOrderDetails().stream()
                .map(this::mapToOrderDetailsDTO)
                .toList();

        dto.setOrderDetails(details);
        return dto;
    }

    private OrderDetailsDTO mapToOrderDetailsDTO(OrderDetails detail) {
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setName(detail.getName());
        dto.setType(detail.getType());
        dto.setPrice(detail.getPrice());
        dto.setQuantity(detail.getQuantity());
        dto.setComments(detail.getComments());
        dto.setCreatedBy(detail.getCreatedBy());
        return dto;
    }


    @Override
    public OrderResponseDTO getOrderByID(Long id) {
        return null;
    }

    @Override
    public OrderResponseDTO getOrderBy(String createdBy) {
        return null;
    }
}