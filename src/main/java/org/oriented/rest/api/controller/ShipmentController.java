package org.oriented.rest.api.controller;

import jakarta.validation.Valid;
import org.oriented.rest.api.service.ShipmentService;
import org.service_oriented.controllers.ShipmentApi;
import org.service_oriented.dto.SaveShipmentDTO;
import org.service_oriented.dto.ShipmentDTO;
import org.service_oriented.dto.UpdateShipmentDTO;
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
@RequestMapping("/api/shipment")
public class ShipmentController implements ShipmentApi {
    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping
    public PagedModel<EntityModel<ShipmentDTO>> getAllShipments(
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ShipmentDTO> shipments = shipmentService.getShipments(pageable);

        List<EntityModel<ShipmentDTO>> shipmentModels = shipments.stream()
                .map(shipment -> EntityModel.of(shipment,
                        linkTo(methodOn(ShipmentController.class).getShipmentById(shipment.id())).withSelfRel()))
                .collect(Collectors.toList());

        PagedModel<EntityModel<ShipmentDTO>> pagedModel = PagedModel.of(shipmentModels,
                new PagedModel.PageMetadata(shipments.getSize(), shipments.getNumber(), shipments.getTotalElements()));

        String selfUrl = String.format("%s?page=%d&size=%d",
                linkTo(methodOn(ShipmentController.class).getAllShipments(pageable)).toUri().toString(),
                pageable.getPageNumber(), pageable.getPageSize());
        pagedModel.add(Link.of(selfUrl, "self"));

        if (shipments.hasNext()) {
            String nextUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(ShipmentController.class).getAllShipments(pageable)).toUri().toString(),
                    pageable.getPageNumber() + 1, pageable.getPageSize());
            pagedModel.add(Link.of(nextUrl, "next"));
        }
        if (shipments.hasPrevious()) {
            String prevUrl = String.format("%s?page=%d&size=%d",
                    linkTo(methodOn(ShipmentController.class).getAllShipments(pageable)).toUri().toString(),
                    pageable.getPageNumber() - 1, pageable.getPageSize());
            pagedModel.add(Link.of(prevUrl, "prev"));
        }

        return pagedModel;
    }

    @GetMapping("/{id}")
    public EntityModel<ShipmentDTO> getShipmentById(@PathVariable Long id) {
        ShipmentDTO shipment = shipmentService.getShipment(id);
        return EntityModel.of(shipment,
                linkTo(methodOn(ShipmentController.class).getShipmentById(id)).withSelfRel());
    }

    @PostMapping
    public EntityModel<ShipmentDTO> createShipment(@Valid @RequestBody SaveShipmentDTO shipmentDTO) {
        ShipmentDTO createdShipment = shipmentService.saveShipment(shipmentDTO);
        return EntityModel.of(createdShipment,
                linkTo(methodOn(ShipmentController.class).getShipmentById(createdShipment.id())).withSelfRel());
    }

    @PatchMapping("/{id}")
    public EntityModel<ShipmentDTO> updateShipment(@PathVariable Long id, @Valid @RequestBody UpdateShipmentDTO shipmentDTO) {
        ShipmentDTO updatedShipment = shipmentService.updateShipment(id, shipmentDTO);
        return EntityModel.of(updatedShipment,
                linkTo(methodOn(ShipmentController.class).getShipmentById(updatedShipment.id())).withSelfRel());
    }

    @DeleteMapping("/{id}")
    public void deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
    }
}
