package org.oriented.rest.api.controller;

import org.service_oriented.controllers.Api;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api")
public class ApiController implements Api {
    @GetMapping
    public RepresentationModel<?> getApiLinks() {
        RepresentationModel<?> model = new RepresentationModel<>();
        model.add(linkTo(UserController.class).withRel("user"));
        model.add(linkTo(ShipmentController.class).withRel("shipment"));
        model.add(linkTo(PickupPointController.class).withRel("pickup-point"));
        model.add(linkTo(OrderController.class).withRel("order"));
        return model;
    }
}
