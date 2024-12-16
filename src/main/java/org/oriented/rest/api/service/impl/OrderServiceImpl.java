package org.oriented.rest.api.service.impl;

import org.oriented.rabbitmq.producer.MessageCostProducer;
import org.oriented.rest.api.mapper.OrderMapper;
import org.oriented.rest.api.model.Order;
import org.oriented.rest.api.model.Shipment;
import org.oriented.rest.api.service.OrderService;
import org.service_oriented.dto.OrderDTO;
import org.service_oriented.dto.SaveOrderDTO;
import org.service_oriented.dto.UpdateOrderDTO;
import org.service_oriented.exceptions.CustomExceptions;
import org.oriented.rest.api.model.enums.OrderStatus;
import org.oriented.rest.api.repository.OrderRepository;
import org.oriented.rest.api.repository.ShipmentRepository;
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

    private final MessageCostProducer messageCostProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ShipmentRepository shipmentRepository, MessageCostProducer messageCostProducer) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.shipmentRepository = shipmentRepository;
        this.messageCostProducer = messageCostProducer;
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
        List<Shipment> shipments = Optional.ofNullable(dto.shipmentsIds()).map(shipmentRepository::findAllById).orElse(List.of());
        Order order = orderMapper.toOrder(dto, shipments);
        shipments.forEach(shipment -> shipment.setOrder(order));
        OrderDTO orderDTO = orderMapper.toOrderDto(orderRepository.save(order));
        if (!shipments.isEmpty() && dto.totalCost() == 0) {
            messageCostProducer.sendMessage(orderDTO, shipments);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, UpdateOrderDTO dto) {
        Order existingOrder =  findOrderById(id);

        Optional.ofNullable(dto.orderNumber()).ifPresent(existingOrder::setOrderNumber);
        Optional.ofNullable(dto.orderDate()).ifPresent(existingOrder::setOrderDate);
        Optional.ofNullable(dto.totalCost()).filter(cost -> cost >= 0).ifPresent(existingOrder::setTotalCost);
        Optional.ofNullable(dto.status()).ifPresent(status -> existingOrder.setStatus(OrderStatus.valueOf(status.name())));
        List<Shipment> shipments = Optional.ofNullable(dto.shipmentsIds()).map(shipmentRepository::findAllById).orElse(existingOrder.getShipments());
        existingOrder.setShipments(shipments);
        shipments.forEach(shipment -> shipment.setOrder(existingOrder));
        OrderDTO orderDTO = orderMapper.toOrderDto(orderRepository.save(existingOrder));
        if ((dto.shipmentsIds() != null && !dto.shipmentsIds().isEmpty()) || existingOrder.getTotalCost() == 0) {
            messageCostProducer.sendMessage(orderDTO, shipments);
            existingOrder.setTotalCost(0);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new CustomExceptions.OrderNotFoundException("Order not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public OrderDTO updateTotalCost(Long id, double totalCost){
        Order existingOrder =  findOrderById(id);
        existingOrder.setTotalCost(totalCost);
        return orderMapper.toOrderDto(orderRepository.save(existingOrder));
    }

    private Order findOrderById(Long id){
       return  orderRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.OrderNotFoundException("Order not found with id: " + id));
    }


}
