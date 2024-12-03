package org.oriented.rest.api.model;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.oriented.rest.api.model.enums.DeliveryType;
import org.oriented.rest.api.model.enums.ShipmentStatus;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@SuperBuilder
@Entity
@Table(name = "shipments")
public class Shipment extends BaseEntity {
    @Column(nullable = false)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    private double weight;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User recipient;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private PickupPoint pickupPoint;

    private String deliveryAddress;

    private LocalDate shipmentDate;

    private LocalDate estimatedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Order order;

    public Shipment(Long id, ZonedDateTime created_date, ZonedDateTime updated_date, String trackingNumber, ShipmentStatus status, double weight, User sender, User recipient, PickupPoint pickupPoint, String deliveryAddress, LocalDate shipmentDate, LocalDate estimatedDeliveryDate, DeliveryType deliveryType, Order order) {
        super(id, created_date, updated_date);
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.weight = weight;
        this.sender = sender;
        this.recipient = recipient;
        this.pickupPoint = pickupPoint;
        this.deliveryAddress = deliveryAddress;
        this.shipmentDate = shipmentDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.deliveryType = deliveryType;
        this.order = order;
    }

    public Shipment(String trackingNumber, ShipmentStatus status, double weight, User sender, User recipient, PickupPoint pickupPoint, String deliveryAddress, LocalDate shipmentDate, LocalDate estimatedDeliveryDate, DeliveryType deliveryType, Order order) {
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.weight = weight;
        this.sender = sender;
        this.recipient = recipient;
        this.pickupPoint = pickupPoint;
        this.deliveryAddress = deliveryAddress;
        this.shipmentDate = shipmentDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.deliveryType = deliveryType;
        this.order = order;
    }

    protected Shipment() {
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public ShipmentStatus getStatus() {
        return this.status;
    }

    public Double getWeight() {
        return this.weight;
    }

    public User getSender() {
        return this.sender;
    }

    public User getRecipient() {
        return this.recipient;
    }

    public PickupPoint getPickupPoint() {
        return this.pickupPoint;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public LocalDate getShipmentDate() {
        return this.shipmentDate;
    }

    public LocalDate getEstimatedDeliveryDate() {
        return this.estimatedDeliveryDate;
    }

    public DeliveryType getDeliveryType() {
        return this.deliveryType;
    }

    public Order getOrder() {
        return this.order;
    }


    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
