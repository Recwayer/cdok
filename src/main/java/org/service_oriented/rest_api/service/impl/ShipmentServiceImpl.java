package org.service_oriented.rest_api.service.impl;

import org.service_oriented.dto.SaveShipmentDTO;
import org.service_oriented.dto.ShipmentDTO;
import org.service_oriented.dto.UpdateShipmentDTO;
import org.service_oriented.exceptions.CustomExceptions;
import org.service_oriented.rest_api.mapper.ShipmentMapper;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.enums.DeliveryType;
import org.service_oriented.rest_api.model.enums.ShipmentStatus;
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
        Order order = findOrderById(dto.orderId());
        PickupPoint pickupPoint = findPickupPointById(dto.pickupPointId());
        User sender = findUserById(dto.senderId());
        User recipient = findUserById(dto.recipientId());
        return shipmentMapper.toShipmentDTO(shipmentRepository.save(shipmentMapper.toShipment(dto,order,pickupPoint,sender,recipient)));
    }

    @Override
    @Transactional
    public ShipmentDTO updateShipment(Long id, UpdateShipmentDTO dto) {
        Shipment existingShipment = findShipmentById(id);

        Optional.ofNullable(dto.trackingNumber()).ifPresent(existingShipment::setTrackingNumber);
        Optional.ofNullable(dto.status()).ifPresent(status -> existingShipment.setStatus(ShipmentStatus.valueOf(status.name())));
        Optional.ofNullable(dto.weight()).filter(weight -> weight >= 0).ifPresent(existingShipment::setWeight);
        Optional.ofNullable(dto.deliveryAddress()).ifPresent(existingShipment::setDeliveryAddress);
        Optional.ofNullable(dto.shipmentDate()).ifPresent(existingShipment::setShipmentDate);
        Optional.ofNullable(dto.estimatedDeliveryDate()).ifPresent(existingShipment::setEstimatedDeliveryDate);
        Optional.ofNullable(dto.deliveryType()).ifPresent(deliveryType -> existingShipment.setDeliveryType(DeliveryType.valueOf(deliveryType.name())));
        Order order = Optional.ofNullable(dto.orderId()).map(this::findOrderById).orElse(existingShipment.getOrder());
        existingShipment.setOrder(order);
        PickupPoint pickupPoint = Optional.ofNullable(dto.pickupPointId()).map(this::findPickupPointById).orElse(existingShipment.getPickupPoint());
        existingShipment.setPickupPoint(pickupPoint);
        User sender = Optional.ofNullable(dto.senderId()).map(this::findUserById).orElse(existingShipment.getSender());
        existingShipment.setSender(sender);
        User recipient = Optional.ofNullable(dto.recipientId()).map(this::findUserById).orElse(existingShipment.getRecipient());
        existingShipment.setRecipient(recipient);
        return shipmentMapper.toShipmentDTO(shipmentRepository.save(existingShipment));
    }

    @Override
    @Transactional
    public void deleteShipment(Long id) {
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
        } else {
            throw new CustomExceptions.ShipmentNotFoundException("Shipment not found with id: " + id);
        }
    }

    private Shipment findShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ShipmentNotFoundException("Shipment not found with id: " + id));
    }

    private Order findOrderById(Long id){
      return   orderRepository.findById(id).orElseThrow(() -> new CustomExceptions.OrderNotFoundException("Order not found with id: " + id));
    }

    private PickupPoint findPickupPointById(Long id){
       return pickupPointRepository.findById(id).orElseThrow(() -> new CustomExceptions.PickupPointNotFoundException("PickupPoint not found with id: " + id));
    }

    private User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new CustomExceptions.UserNotFoundException("User not found with id: " + id));
    }
}
