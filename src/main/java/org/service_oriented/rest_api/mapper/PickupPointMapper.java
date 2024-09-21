package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.dtos.PickupPointDTO;
import org.service_oriented.rest_api.model.dtos.SavePickupPointDTO;
import org.service_oriented.rest_api.model.dtos.ShipmentDTO;
import org.service_oriented.rest_api.model.dtos.UpdatePickupPointDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PickupPointMapper {
   public abstract PickupPointDTO toPickupPointDTO(PickupPoint pickupPoint);

   @Mapping(target = "availableShipments", expression = "java(getShipmentsFromIds(pickupPointDTO.getAvailableShipmentsIds()))")
   public abstract PickupPoint toPickupPoint(SavePickupPointDTO pickupPointDTO);
   @Mapping(target = "availableShipments", expression = "java(getShipmentsFromIds(availableShipments))")
   public abstract PickupPoint toPickupPoint(PickupPointDTO pickupPointDTO, List<Long> availableShipments);

   public List<Shipment> getShipmentsFromIds(List<Long> shipmentIds){
      if(shipmentIds == null){
         return null;
      }
      return shipmentIds.stream()
              .map(id -> (Shipment) Shipment.builder().id(id).build())
              .toList();
   }
}
