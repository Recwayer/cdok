package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.service_oriented.rest_api.model.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveOrderDTO {
    @NotNull(message = "Order number is required")
    private String orderNumber;
    private List<Long> shipmentsIds;
    @NotNull(message = "Order date is required")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;
    @Min(value = 0, message = "Total cost must be positive")
    private double totalCost;
    @NotNull(message = "Order status is required")
    private OrderStatus status;
}
