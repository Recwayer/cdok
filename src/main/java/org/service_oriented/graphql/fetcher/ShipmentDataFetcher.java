package org.service_oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;
import org.service_oriented.rest_api.model.dtos.SaveShipmentDTO;
import org.service_oriented.rest_api.model.dtos.ShipmentDTO;
import org.service_oriented.rest_api.model.dtos.UpdateShipmentDTO;
import org.service_oriented.rest_api.service.ShipmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class ShipmentDataFetcher {

    private final ShipmentService shipmentService;


    public ShipmentDataFetcher(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @DgsQuery
    public ShipmentDTO getShipmentById(Long id) {
        return shipmentService.getShipment(id);
    }

    @DgsQuery
    public Page<ShipmentDTO> getAllShipments(int page, int size) {
        return shipmentService.getShipments(PageRequest.of(page, size));
    }

    @DgsMutation
    public ShipmentDTO createShipment(SaveShipmentDTO shipment) {
        return shipmentService.saveShipment(shipment);
    }

    @DgsMutation
    public ShipmentDTO updateShipment(Long id, UpdateShipmentDTO shipment) {
        return shipmentService.updateShipment(id, shipment);
    }

    @DgsMutation
    public Boolean deleteShipment(Long id) {
        shipmentService.deleteShipment(id);
        return true;
    }
}
