package org.service_oriented.rest_api.service.impl;

import org.service_oriented.rest_api.mapper.OrderMapper;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.dtos.OrderDTO;
import org.service_oriented.rest_api.model.dtos.SaveOrderDTO;
import org.service_oriented.rest_api.model.dtos.UpdateOrderDTO;
import org.service_oriented.rest_api.repository.OrderRepository;
import org.service_oriented.rest_api.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Page<OrderDTO> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toOrderDto);
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return orderMapper.toOrderDto(orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id)));
    }

    @Override
    @Transactional
    public OrderDTO saveOrder(SaveOrderDTO dto) {
        return orderMapper.toOrderDto(orderRepository.save(orderMapper.toOrder(dto)));
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, UpdateOrderDTO dto) {
        OrderDTO existingOrder = getOrder(id);

        Optional.ofNullable(dto.getOrderNumber()).ifPresent(existingOrder::setOrderNumber);
        Optional.ofNullable(dto.getShipments()).ifPresent(existingOrder::setShipments);
        Optional.ofNullable(dto.getOrderDate()).ifPresent(existingOrder::setOrderDate);
        Optional.ofNullable(dto.getTotalCost()).filter(cost -> cost >= 0).ifPresent(existingOrder::setTotalCost);
        Optional.ofNullable(dto.getStatus()).ifPresent(existingOrder::setStatus);

        return orderMapper.toOrderDto(orderRepository.save(orderMapper.toOrder(existingOrder)));
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
    }


}
