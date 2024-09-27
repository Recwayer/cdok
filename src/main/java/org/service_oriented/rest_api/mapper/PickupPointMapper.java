package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.rest_api.model.Address;
import org.service_oriented.rest_api.model.PickupPoint;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.dtos.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PickupPointMapper {
    public abstract PickupPointDTO toPickupPointDTO(PickupPoint pickupPoint);


    public abstract PickupPoint toPickupPoint(SavePickupPointDTO pickupPointDTO, List<Shipment> availableShipments);

    public abstract Address mapAddressDTOToAddress(AddressDTO addressDTO);
}
