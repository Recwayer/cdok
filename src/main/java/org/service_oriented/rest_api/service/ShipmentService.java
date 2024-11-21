package org.service_oriented.rest_api.service;

import org.service_oriented.dto.SaveShipmentDTO;
import org.service_oriented.dto.ShipmentDTO;
import org.service_oriented.dto.UpdateShipmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShipmentService {
    Page<ShipmentDTO> getShipments(Pageable pageable);

    ShipmentDTO getShipment(Long id);

    ShipmentDTO saveShipment(SaveShipmentDTO dto);


    ShipmentDTO updateShipment(Long id, UpdateShipmentDTO dto);

    void deleteShipment(Long id);
}
