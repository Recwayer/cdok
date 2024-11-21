package org.service_oriented.rest_api.service;

import org.service_oriented.dto.PickupPointDTO;
import org.service_oriented.dto.SavePickupPointDTO;
import org.service_oriented.dto.UpdatePickupPointDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PickupPointService {
    Page<PickupPointDTO> getPickupPoints(Pageable pageable);

    PickupPointDTO getPickupPoint(Long id);

    PickupPointDTO updatePickupPoint(Long id, UpdatePickupPointDTO dto);

    PickupPointDTO savePickupPoint(SavePickupPointDTO dto);

    void deletePickupPoint(Long id);
}
