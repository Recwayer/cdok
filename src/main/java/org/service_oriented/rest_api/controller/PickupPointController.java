package org.service_oriented.rest_api.controller;

import jakarta.validation.Valid;
import org.service_oriented.controllers.PickupPointApi;
import org.service_oriented.dto.PickupPointDTO;
import org.service_oriented.dto.SavePickupPointDTO;
import org.service_oriented.dto.UpdatePickupPointDTO;
import org.service_oriented.rest_api.service.PickupPointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pickup-point")
public class PickupPointController implements PickupPointApi {
    private PickupPointService pickupPointService;

    public PickupPointController(PickupPointService pickupPointService) {
        this.pickupPointService = pickupPointService;
    }

    public void setPickupPointService(PickupPointService pickupPointService) {
        this.pickupPointService = pickupPointService;
    }

    @GetMapping
    public PagedModel<EntityModel<PickupPointDTO>> getAllPickupPoints(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            @PageableDefault(size = 10) Pageable pageable) {

        Page<PickupPointDTO> pickupPoints = pickupPointService.getPickupPoints(pageable);

        List<EntityModel<PickupPointDTO>> pickupPointModels = pickupPoints.stream()
                .map(point -> EntityModel.of(point,
                        linkTo(methodOn(PickupPointController.class).getPickupPointById(point.id())).withSelfRel()))
                .collect(Collectors.toList());

        PagedModel<EntityModel<PickupPointDTO>> pagedModel = PagedModel.of(pickupPointModels,
                new PagedModel.PageMetadata(pickupPoints.getSize(), pickupPoints.getNumber(), pickupPoints.getTotalElements()));

        String selfUrl = String.format("%s?page=%d&size=%d",
                linkTo(methodOn(PickupPointController.class).getAllPickupPoints(pageable)).toUri().toString(),
                pageable.getPageNumber(), pageable.getPageSize());
        pagedModel.add(Link.of(selfUrl, "self"));

        if (pickupPoints.hasNext()) {
            String nextUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(PickupPointController.class).getAllPickupPoints(pageable)).toUri().toString(),
                    pageable.getPageNumber() + 1, pageable.getPageSize());
            pagedModel.add(Link.of(nextUrl, "next"));
        }
        if (pickupPoints.hasPrevious()) {
            String prevUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(PickupPointController.class).getAllPickupPoints(pageable)).toUri().toString(),
                    pageable.getPageNumber() - 1, pageable.getPageSize());
            pagedModel.add(Link.of(prevUrl, "prev"));
        }

        return pagedModel;
    }

    @GetMapping("/{id}")
    public EntityModel<PickupPointDTO> getPickupPointById(@PathVariable Long id) {
        PickupPointDTO pickupPoint = pickupPointService.getPickupPoint(id);
        return EntityModel.of(pickupPoint,
                linkTo(methodOn(PickupPointController.class).getPickupPointById(id)).withSelfRel());
    }

    @PostMapping
    public EntityModel<PickupPointDTO> createPickupPoint(@Valid @RequestBody SavePickupPointDTO pickupPointDTO) {
        PickupPointDTO createdPickupPoint = pickupPointService.savePickupPoint(pickupPointDTO);
        return EntityModel.of(createdPickupPoint,
                linkTo(methodOn(PickupPointController.class).getPickupPointById(createdPickupPoint.id())).withSelfRel());
    }

    @PatchMapping("/{id}")
    public EntityModel<PickupPointDTO> updatePickupPoint(@PathVariable Long id, @Valid @RequestBody UpdatePickupPointDTO pickupPointDTO) {
        PickupPointDTO updatedPickupPoint = pickupPointService.updatePickupPoint(id, pickupPointDTO);
        return EntityModel.of(updatedPickupPoint,
                linkTo(methodOn(PickupPointController.class).getPickupPointById(updatedPickupPoint.id())).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public void deletePickupPoint(@PathVariable Long id) {
        pickupPointService.deletePickupPoint(id);
    }
}
