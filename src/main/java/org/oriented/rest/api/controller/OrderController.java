package org.oriented.rest.api.controller;

import jakarta.validation.Valid;
import org.oriented.rest.api.service.OrderService;
import org.service_oriented.controllers.OrderApi;

import org.service_oriented.dto.OrderDTO;
import org.service_oriented.dto.SaveOrderDTO;
import org.service_oriented.dto.UpdateOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/order")
public class OrderController implements OrderApi {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public PagedModel<EntityModel<OrderDTO>> getAllOrders(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            @PageableDefault(size = 10) Pageable pageable) {

        Page<OrderDTO> orders = orderService.getOrders(pageable);

        List<EntityModel<OrderDTO>> orderModels = orders.stream()
                .map(order -> EntityModel.of(order,
                        linkTo(methodOn(OrderController.class).getOrderById(order.id())).withSelfRel()))
                .collect(Collectors.toList());

        PagedModel<EntityModel<OrderDTO>> pagedModel = PagedModel.of(orderModels,
                new PagedModel.PageMetadata(orders.getSize(), orders.getNumber(), orders.getTotalElements()));

        String selfUrl = String.format("%s?page=%d&size=%d",
                linkTo(methodOn(OrderController.class).getAllOrders(pageable)).toUri().toString(),
                pageable.getPageNumber(), pageable.getPageSize());
        pagedModel.add(Link.of(selfUrl, "self"));

        if (orders.hasNext()) {
            String nextUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(OrderController.class).getAllOrders(pageable)).toUri().toString(),
                    pageable.getPageNumber() + 1, pageable.getPageSize());
            pagedModel.add(Link.of(nextUrl, "next"));
        }
        if (orders.hasPrevious()) {
            String prevUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(OrderController.class).getAllOrders(pageable)).toUri().toString(),
                    pageable.getPageNumber() - 1, pageable.getPageSize());
            pagedModel.add(Link.of(prevUrl, "prev"));
        }

        return pagedModel;
    }

    @GetMapping("/{id}")
    public EntityModel<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrder(id);
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(id)).withSelfRel());
    }

    @PostMapping
    public EntityModel<OrderDTO> createOrder(@Valid @RequestBody SaveOrderDTO orderDTO) {
        OrderDTO createdOrder = orderService.saveOrder(orderDTO);
        return EntityModel.of(createdOrder,
                linkTo(methodOn(OrderController.class).getOrderById(createdOrder.id())).withSelfRel());
    }

    @PatchMapping("/{id}")
    public EntityModel<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderDTO orderDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        return EntityModel.of(updatedOrder,
                linkTo(methodOn(OrderController.class).getOrderById(updatedOrder.id())).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
