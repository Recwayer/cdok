package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.dtos.OrderDTO;
import org.service_oriented.rest_api.model.dtos.SaveOrderDTO;
import org.service_oriented.rest_api.model.dtos.UpdateOrderDTO;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrderMapper {
    public abstract OrderDTO toOrderDto(Order order);

    @Mapping(target = "shipments", expression = "java(getShipmentsFromIds(orderDTO.getShipmentsIds()))")
    public abstract Order toOrder(SaveOrderDTO orderDTO);

    @Mapping(target = "shipments", expression = "java(getShipmentsFromIds(shipmentIds))")
    public abstract Order toOrder(OrderDTO orderDTO, List<Long> shipmentIds);

    public List<Shipment> getShipmentsFromIds(List<Long> shipmentIds){
        if(shipmentIds == null){
            return null;
        }
        return shipmentIds.stream()
                .map(id -> (Shipment) Shipment.builder().id(id).build())
                .toList();
    }
}
