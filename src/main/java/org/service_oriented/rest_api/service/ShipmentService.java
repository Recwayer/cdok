package org.service_oriented.rest_api.service;

import org.service_oriented.rest_api.model.Shipment;
import org.service_oriented.rest_api.model.dtos.SaveShipmentDTO;
import org.service_oriented.rest_api.model.dtos.ShipmentDTO;
import org.service_oriented.rest_api.model.dtos.UpdateShipmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShipmentService {
    Page<ShipmentDTO> getShipments(Pageable pageable);

    ShipmentDTO getShipment(Long id);

    ShipmentDTO saveShipment(SaveShipmentDTO dto);


    ShipmentDTO updateShipment(Long id, UpdateShipmentDTO dto);

    void deleteShipment(Long id);
}
