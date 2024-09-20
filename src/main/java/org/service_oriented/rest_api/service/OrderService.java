package org.service_oriented.rest_api.service;

import org.service_oriented.rest_api.model.Order;
import org.service_oriented.rest_api.model.dtos.OrderDTO;
import org.service_oriented.rest_api.model.dtos.SaveOrderDTO;
import org.service_oriented.rest_api.model.dtos.UpdateOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDTO> getOrders(Pageable pageable);

    OrderDTO getOrder(Long id);

    OrderDTO saveOrder(SaveOrderDTO entity);

    OrderDTO updateOrder(Long id, UpdateOrderDTO entity);

    void deleteOrder(Long id);
}
