package org.service_oriented.rest_api.mapper;

import org.mapstruct.Mapper;
import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.dtos.OrderDTO;
import org.service_oriented.rest_api.model.dtos.SaveOrderDTO;
import org.service_oriented.rest_api.model.dtos.UpdateOrderDTO;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {

    public abstract OrderDTO toOrderDto(Order order);

    public abstract Order toOrder(OrderDTO orderDTO);

    public abstract Order toOrder(SaveOrderDTO orderDTO);

    public abstract Order toOrder(UpdateOrderDTO orderDTO);
}
