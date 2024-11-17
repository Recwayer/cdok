package org.service_oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import org.service_oriented.dto.SaveShipmentDTO;
import org.service_oriented.dto.ShipmentDTO;
import org.service_oriented.dto.UpdateShipmentDTO;
import org.service_oriented.fetchers.ShipmentDataFetcherApi;
import org.service_oriented.rest_api.service.ShipmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class ShipmentDataFetcher implements ShipmentDataFetcherApi {

    private final ShipmentService shipmentService;


    public ShipmentDataFetcher(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }


    public ShipmentDTO getShipmentById(Long id) {
        return shipmentService.getShipment(id);
    }


    public Page<ShipmentDTO> getAllShipments(int page, int size) {
        return shipmentService.getShipments(PageRequest.of(page, size));
    }


    public ShipmentDTO createShipment(SaveShipmentDTO shipment) {
        return shipmentService.saveShipment(shipment);
    }


    public ShipmentDTO updateShipment(Long id, UpdateShipmentDTO shipment) {
        return shipmentService.updateShipment(id, shipment);
    }


    public Boolean deleteShipment(Long id) {
        shipmentService.deleteShipment(id);
        return true;
    }
}
