package org.service_oriented.graphql.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.service_oriented.rest_api.model.dtos.PickupPointDTO;
import org.service_oriented.rest_api.model.dtos.SavePickupPointDTO;
import org.service_oriented.rest_api.model.dtos.UpdatePickupPointDTO;
import org.service_oriented.rest_api.service.PickupPointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DgsComponent
public class PickupPointDataFetcher {

    private final PickupPointService pickupPointService;

    public PickupPointDataFetcher(PickupPointService pickupPointService) {
        this.pickupPointService = pickupPointService;
    }

    @DgsQuery
    public Page<PickupPointDTO> getAllPickupPoints(int page, int size) {
        return pickupPointService.getPickupPoints(PageRequest.of(page, size));
    }

    @DgsQuery
    public PickupPointDTO getPickupPointById(Long id) {
        return pickupPointService.getPickupPoint(id);
    }

    @DgsMutation
    public PickupPointDTO createPickupPoint(SavePickupPointDTO input) {
        return pickupPointService.savePickupPoint(input);
    }

    @DgsMutation
    public PickupPointDTO updatePickupPoint(Long id, UpdatePickupPointDTO input) {
        return pickupPointService.updatePickupPoint(id, input);
    }

    @DgsMutation
    public boolean deletePickupPoint(Long id) {
        pickupPointService.deletePickupPoint(id);
        return true;
    }
}
