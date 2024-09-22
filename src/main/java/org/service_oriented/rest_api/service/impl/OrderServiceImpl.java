package org.service_oriented.rest_api.service.impl;

import org.service_oriented.rest_api.mapper.OrderMapper;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.dtos.OrderDTO;
import org.service_oriented.rest_api.model.dtos.SaveOrderDTO;
import org.service_oriented.rest_api.model.dtos.UpdateOrderDTO;
import org.service_oriented.rest_api.repository.OrderRepository;
import org.service_oriented.rest_api.repository.ShipmentRepository;
import org.service_oriented.rest_api.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private OrderMapper orderMapper;

    private ShipmentRepository shipmentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ShipmentRepository shipmentRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.shipmentRepository =shipmentRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Page<OrderDTO> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toOrderDto);
    }

    @Override
    public OrderDTO getOrder(Long id) {
        return orderMapper.toOrderDto(findOrderById(id));
    }

    @Override
    @Transactional
    public OrderDTO saveOrder(SaveOrderDTO dto) {
        List<Shipment> shipments = Optional.ofNullable(dto.getShipmentsIds()).map(shipmentRepository::findAllById).orElse(List.of());
        Order order = orderMapper.toOrder(dto,shipments);
        shipments.forEach(shipment -> shipment.setOrder(order));
        return orderMapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, UpdateOrderDTO dto) {
        Order existingOrder =  findOrderById(id);

        Optional.ofNullable(dto.getOrderNumber()).ifPresent(existingOrder::setOrderNumber);
        Optional.ofNullable(dto.getOrderDate()).ifPresent(existingOrder::setOrderDate);
        Optional.ofNullable(dto.getTotalCost()).filter(cost -> cost >= 0).ifPresent(existingOrder::setTotalCost);
        Optional.ofNullable(dto.getStatus()).ifPresent(existingOrder::setStatus);
        List<Shipment> shipments = Optional.ofNullable(dto.getShipmentsIds()).map(shipmentRepository::findAllById).orElse(existingOrder.getShipments());
        existingOrder.setShipments(shipments);
        shipments.forEach(shipment -> shipment.setOrder(existingOrder));
        return orderMapper.toOrderDto(orderRepository.save(existingOrder));
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

    private Order findOrderById(Long id){
       return  orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }


}
