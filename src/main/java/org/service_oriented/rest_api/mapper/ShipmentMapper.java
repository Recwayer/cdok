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

    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "recipient", source = "recipient")
    @Mapping(target = "pickupPoint", source = "pickupPoint")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "id", ignore = true)
    public abstract Shipment toShipment(SaveShipmentDTO dto,Order order,PickupPoint pickupPoint,User sender,User recipient);
}
