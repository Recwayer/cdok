package org.service_oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.service_oriented.rest_api.model.dtos.OrderDTO;
import org.service_oriented.rest_api.model.dtos.SaveOrderDTO;
import org.service_oriented.rest_api.model.dtos.UpdateOrderDTO;
import org.service_oriented.rest_api.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class OrderDataFetcher {

    private final OrderService orderService;

    public OrderDataFetcher(OrderService orderService) {
        this.orderService = orderService;
    }

    @DgsQuery
    public Page<OrderDTO> getAllOrders(int page, int size) {
        return orderService.getOrders(PageRequest.of(page, size));
    }

    @DgsQuery
    public OrderDTO getOrderById(Long id) {
        return orderService.getOrder(id);
    }

    @DgsMutation
    public OrderDTO createOrder(SaveOrderDTO input) {
        return orderService.saveOrder(input);
    }

    @DgsMutation
    public OrderDTO updateOrder(Long id, UpdateOrderDTO input) {
        return orderService.updateOrder(id, input);
    }

    @DgsMutation
    public boolean deleteOrder(Long id) {
        orderService.deleteOrder(id);
        return true;
    }
}
