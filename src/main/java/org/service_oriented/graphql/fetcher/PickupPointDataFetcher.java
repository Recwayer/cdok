package org.service_oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import org.service_oriented.dto.PickupPointDTO;
import org.service_oriented.dto.SavePickupPointDTO;
import org.service_oriented.dto.UpdatePickupPointDTO;
import org.service_oriented.fetchers.PickupPointDataFetcherApi;
import org.service_oriented.rest_api.service.PickupPointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class PickupPointDataFetcher implements PickupPointDataFetcherApi {

    private final PickupPointService pickupPointService;

    public PickupPointDataFetcher(PickupPointService pickupPointService) {
        this.pickupPointService = pickupPointService;
    }


    public Page<PickupPointDTO> getAllPickupPoints(int page, int size) {
        return pickupPointService.getPickupPoints(PageRequest.of(page, size));
    }


    public PickupPointDTO getPickupPointById(Long id) {
        return pickupPointService.getPickupPoint(id);
    }


    public PickupPointDTO createPickupPoint(SavePickupPointDTO input) {
        return pickupPointService.savePickupPoint(input);
    }


    public PickupPointDTO updatePickupPoint(Long id, UpdatePickupPointDTO input) {
        return pickupPointService.updatePickupPoint(id, input);
    }


    public boolean deletePickupPoint(Long id) {
        pickupPointService.deletePickupPoint(id);
        return true;
    }
}
