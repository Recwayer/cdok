package org.service_oriented.rest_api.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePickupPointDTO {
    @NotBlank(message = "Name is required")
    private String name;
    private AddressDTO address;
    @NotBlank(message = "Working hours are required")
    private String workingHours;
    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;
    private List<Long> availableShipmentsIds;
}
