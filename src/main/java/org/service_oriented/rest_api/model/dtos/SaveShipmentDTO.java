package org.service_oriented.rest_api.model.dtos;

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
    private String trackingNumber;
    private ShipmentStatus status;
    private double weight;
    private UserDTO sender;
    private UserDTO recipient;
    private PickupPointDTO pickupPoint;
    private String deliveryAddress;
    private LocalDate shipmentDate;
    private LocalDate estimatedDeliveryDate;
    private DeliveryType deliveryType;
    private OrderDTO order;
}
