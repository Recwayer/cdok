package org.oriented.rest.api.service;

import org.service_oriented.dto.OrderDTO;
import org.service_oriented.dto.SaveOrderDTO;
import org.service_oriented.dto.UpdateOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDTO> getOrders(Pageable pageable);

    OrderDTO getOrder(Long id);

    OrderDTO saveOrder(SaveOrderDTO entity);

    OrderDTO updateOrder(Long id, UpdateOrderDTO entity);

    void deleteOrder(Long id);

    OrderDTO updateTotalCost(Long id, double totalCost);
}
