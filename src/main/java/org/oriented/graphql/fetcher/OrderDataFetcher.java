package org.oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import org.oriented.rest.api.service.OrderService;
import org.service_oriented.dto.OrderDTO;
import org.service_oriented.dto.SaveOrderDTO;
import org.service_oriented.dto.UpdateOrderDTO;
import org.service_oriented.fetchers.OrderDataFetcherApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class OrderDataFetcher implements OrderDataFetcherApi {

    private final OrderService orderService;

    public OrderDataFetcher(OrderService orderService) {
        this.orderService = orderService;
    }


    public Page<OrderDTO> getAllOrders(int page, int size) {
        return orderService.getOrders(PageRequest.of(page, size));
    }


    public OrderDTO getOrderById(Long id) {
        return orderService.getOrder(id);
    }


    public OrderDTO createOrder(SaveOrderDTO input) {
        return orderService.saveOrder(input);
    }


    public OrderDTO updateOrder(Long id, UpdateOrderDTO input) {
        return orderService.updateOrder(id, input);
    }


    public boolean deleteOrder(Long id) {
        orderService.deleteOrder(id);
        return true;
    }
}
