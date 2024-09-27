package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Min;
import lombok.*;
import org.service_oriented.rest_api.model.enums.DeliveryType;
import org.service_oriented.rest_api.model.enums.ShipmentStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateShipmentDTO {
    private String trackingNumber;
    private ShipmentStatus status;
    @Min(value = 0, message = "Weight must be non-negative")
    private Double weight;
    private Long senderId;
    private Long recipientId;
    private Long pickupPointId;
    private String deliveryAddress;
    private LocalDate shipmentDate;
    private LocalDate estimatedDeliveryDate;
    private DeliveryType deliveryType;
    private Long orderId;
}
