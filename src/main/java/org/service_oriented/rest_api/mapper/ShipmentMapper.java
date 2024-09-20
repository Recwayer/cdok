package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.dtos.SaveShipmentDTO;
import org.service_oriented.rest_api.model.dtos.ShipmentDTO;
import org.service_oriented.rest_api.model.dtos.UpdateShipmentDTO;

@Mapper(componentModel = "spring")
public abstract class ShipmentMapper {

    public abstract ShipmentDTO toShipmentDTO(Shipment shipment);

    public abstract Shipment toShipment(ShipmentDTO shipmentDTO);

    public abstract Shipment toShipment(SaveShipmentDTO shipmentDTO);

    public abstract Shipment toShipment(UpdateShipmentDTO shipmentDTO);
}
