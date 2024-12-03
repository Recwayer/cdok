package org.oriented.rest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.oriented.rest.api.model.PickupPoint;
import org.oriented.rest.api.model.Shipment;
import org.service_oriented.dto.AddressDTO;
import org.service_oriented.dto.PickupPointDTO;
import org.service_oriented.dto.SavePickupPointDTO;
import org.oriented.rest.api.model.Address;


import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PickupPointMapper {
    public abstract PickupPointDTO toPickupPointDTO(PickupPoint pickupPoint);


    public abstract PickupPoint toPickupPoint(SavePickupPointDTO pickupPointDTO, List<Shipment> availableShipments);

    public abstract Address mapAddressDTOToAddress(AddressDTO addressDTO);
}
