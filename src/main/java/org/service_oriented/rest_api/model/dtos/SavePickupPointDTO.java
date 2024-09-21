package org.service_oriented.rest_api.model.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePickupPointDTO {
    private String name;
    private AddressDTO address;
    private String workingHours;
    private int capacity;
    private List<Long> availableShipmentsIds;
}
