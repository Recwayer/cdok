package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePickupPointDTO {
    private String name;
    private AddressDTO address;
    private String workingHours;
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;
    private List<Long> availableShipmentsIds;
}
