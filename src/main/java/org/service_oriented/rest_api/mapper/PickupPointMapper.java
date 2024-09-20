package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.dtos.PickupPointDTO;
import org.service_oriented.rest_api.model.dtos.SavePickupPointDTO;
import org.service_oriented.rest_api.model.dtos.UpdatePickupPointDTO;

@Mapper(componentModel = "spring")
public abstract class PickupPointMapper {

   public abstract PickupPointDTO toPickupPointDTO(PickupPoint pickupPoint);

   public abstract PickupPoint toPickupPoint(PickupPointDTO pickupPointDTO);

   public abstract PickupPoint toPickupPoint(SavePickupPointDTO pickupPointDTO);

   public abstract PickupPoint toPickupPoint(UpdatePickupPointDTO pickupPointDTO);
}
