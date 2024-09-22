package org.service_oriented.rest_api.service.impl;

import org.service_oriented.rest_api.mapper.ShipmentMapper;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.dtos.SaveShipmentDTO;
import org.service_oriented.rest_api.model.dtos.ShipmentDTO;
import org.service_oriented.rest_api.model.dtos.UpdateShipmentDTO;
import org.service_oriented.rest_api.repository.OrderRepository;
import org.service_oriented.rest_api.repository.PickupPointRepository;
import org.service_oriented.rest_api.repository.ShipmentRepository;
import org.service_oriented.rest_api.repository.UserRepository;
import org.service_oriented.rest_api.service.ShipmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private ShipmentRepository shipmentRepository;
    private ShipmentMapper shipmentMapper;

    private UserRepository userRepository;

    private PickupPointRepository pickupPointRepository;

    private OrderRepository orderRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository, ShipmentMapper shipmentMapper, UserRepository userRepository, PickupPointRepository pickupPointRepository, OrderRepository orderRepository) {
        this.shipmentRepository = shipmentRepository;
        this.shipmentMapper = shipmentMapper;
        this.userRepository = userRepository;
        this.pickupPointRepository = pickupPointRepository;
        this.orderRepository = orderRepository;
    }

    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public void setShipmentMapper(ShipmentMapper shipmentMapper) {
        this.shipmentMapper = shipmentMapper;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setPickupPointRepository(PickupPointRepository pickupPointRepository) {
        this.pickupPointRepository = pickupPointRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<ShipmentDTO> getShipments(Pageable pageable) {
        return shipmentRepository.findAll(pageable).map(shipmentMapper::toShipmentDTO);
    }

    @Override
    public ShipmentDTO getShipment(Long id) {
        return shipmentMapper.toShipmentDTO(findShipmentById(id));
    }

    @Override
    @Transactional
    public ShipmentDTO saveShipment(SaveShipmentDTO dto) {
        Order order = findOrderById(dto.getOrderId());
        PickupPoint pickupPoint = findPickupPointById(dto.getPickupPointId());
        User sender = findUserById(dto.getSenderId());
        User recipient = findUserById(dto.getRecipientId());
        return shipmentMapper.toShipmentDTO(shipmentRepository.save(shipmentMapper.toShipment(dto,order,pickupPoint,sender,recipient)));
    }

    @Override
    @Transactional
    public ShipmentDTO updateShipment(Long id, UpdateShipmentDTO dto) {
        Shipment existingShipment = findShipmentById(id);

        Optional.ofNullable(dto.getTrackingNumber()).ifPresent(existingShipment::setTrackingNumber);
        Optional.ofNullable(dto.getStatus()).ifPresent(existingShipment::setStatus);
        Optional.ofNullable(dto.getWeight()).filter(weight -> weight >= 0).ifPresent(existingShipment::setWeight);
        Optional.ofNullable(dto.getDeliveryAddress()).ifPresent(existingShipment::setDeliveryAddress);
        Optional.ofNullable(dto.getShipmentDate()).ifPresent(existingShipment::setShipmentDate);
        Optional.ofNullable(dto.getEstimatedDeliveryDate()).ifPresent(existingShipment::setEstimatedDeliveryDate);
        Optional.ofNullable(dto.getDeliveryType()).ifPresent(existingShipment::setDeliveryType);
        Order order = Optional.ofNullable(dto.getOrderId()).map(this::findOrderById).orElse(existingShipment.getOrder());
        existingShipment.setOrder(order);
        PickupPoint pickupPoint = Optional.ofNullable(dto.getPickupPointId()).map(this::findPickupPointById).orElse(existingShipment.getPickupPoint());
        existingShipment.setPickupPoint(pickupPoint);
        User sender = Optional.ofNullable(dto.getSenderId()).map(this::findUserById).orElse(existingShipment.getSender());
        existingShipment.setSender(sender);
        User recipient = Optional.ofNullable(dto.getRecipientId()).map(this::findUserById).orElse(existingShipment.getRecipient());
        existingShipment.setRecipient(recipient);
        return shipmentMapper.toShipmentDTO(shipmentRepository.save(existingShipment));
    }

    @Override
    @Transactional
    public void deleteShipment(Long id) {
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Shipment not found with id: " + id);
        }
    }

    private Shipment findShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shipment not found with id: " + id));
    }

    private Order findOrderById(Long id){
      return   orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    private PickupPoint findPickupPointById(Long id){
       return pickupPointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("PickupPoint not found with id: " + id));
    }

    private User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }
}
