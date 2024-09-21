package org.service_oriented.rest_api.model.dtos;

import lombok.*;
import org.service_oriented.rest_api.model.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderDTO {
    private String orderNumber;
    private List<Long> shipmentsIds;
    private LocalDate orderDate;
    private Double totalCost;
    private OrderStatus status;
}
