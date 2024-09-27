package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.service_oriented.rest_api.model.enums.DeliveryType;
import org.service_oriented.rest_api.model.enums.ShipmentStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveShipmentDTO {
    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;
    @NotNull(message = "Status is required")
    private ShipmentStatus status;
    @Min(value = 0, message = "Weight must be non-negative")
    private double weight;
    @NotNull(message = "Sender ID is required")
    private Long senderId;
    @NotNull(message = "Recipient ID is required")
    private Long recipientId;
    private Long pickupPointId;
    private String deliveryAddress;
    private LocalDate shipmentDate;
    private LocalDate estimatedDeliveryDate;
    @NotNull(message = "Delivery type is required")
    private DeliveryType deliveryType;
    private Long orderId;
}
