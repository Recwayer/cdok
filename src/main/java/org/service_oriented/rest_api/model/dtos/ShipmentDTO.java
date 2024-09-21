package org.service_oriented.rest_api.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.service_oriented.rest_api.model.enums.DeliveryType;
import org.service_oriented.rest_api.model.enums.ShipmentStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDTO {
    private Long id;
    private String trackingNumber;
    private ShipmentStatus status;
    private double weight;
    private UserDTO sender;
    private UserDTO recipient;
    private String deliveryAddress;
    private LocalDate shipmentDate;
    private LocalDate estimatedDeliveryDate;
    private DeliveryType deliveryType;
}
