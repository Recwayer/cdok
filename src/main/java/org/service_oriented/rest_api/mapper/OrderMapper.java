package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.dto.OrderDTO;
import org.service_oriented.dto.SaveOrderDTO;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.Shipment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {
    public abstract OrderDTO toOrderDto(Order order);


    public abstract Order toOrder(SaveOrderDTO orderDTO, List<Shipment> shipments);
}