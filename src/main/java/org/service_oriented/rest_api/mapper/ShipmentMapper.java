package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.User;
import org.service_oriented.rest_api.model.dtos.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ShipmentMapper {

    public abstract ShipmentDTO toShipmentDTO(Shipment shipment);

    public abstract Shipment toShipment(ShipmentDTO shipmentDTO);

    @Mapping(target = "sender", expression = "java(getSender(shipmentDTO.getSenderId()))")
    @Mapping(target = "recipient", expression = "java(getRecipient(shipmentDTO.getRecipientId()))")
    @Mapping(target = "pickupPoint", expression = "java(getPickupPoint(shipmentDTO.getPickupPointId()))")
    @Mapping(target = "order", expression = "java(getOrder(shipmentDTO.getOrderId()))")
    public abstract Shipment toShipment(SaveShipmentDTO shipmentDTO);

    @Mapping(target = "sender", expression = "java(getSender(senderId))")
    @Mapping(target = "recipient", expression = "java(getRecipient(recipientId))")
    @Mapping(target = "pickupPoint", expression = "java(getPickupPoint(pickupPointId))")
    @Mapping(target = "order", expression = "java(getOrder(orderId))")
    public abstract Shipment toShipment(ShipmentDTO shipmentDTO, Long senderId, Long recipientId, Long pickupPointId, Long orderId);

    public User getSender(Long id) {
        if (id == null) {
            return null;
        }
        return User.builder().id(id).build();
    }

    public User getRecipient(Long id) {
        if (id == null) {
            return null;
        }
        return User.builder().id(id).build();
    }

    public PickupPoint getPickupPoint(Long id) {
        if (id == null) {
            return null;
        }
        return PickupPoint.builder().id(id).build();
    }

    public Order getOrder(Long id) {
        if (id == null) {
            return null;
        }
        return Order.builder().id(id).build();
    }
}
