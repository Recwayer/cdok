package org.service_oriented.rest_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.service_oriented.rest_api.model.enums.DeliveryType;
import org.service_oriented.rest_api.model.enums.ShipmentStatus;

import java.time.LocalDate;

@Entity
@Table(name = "shipments")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    private double weight;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @ManyToOne
    private PickupPoint pickupPoint;

    private String deliveryAddress;

    private LocalDate shipmentDate;

    private LocalDate estimatedDeliveryDate;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    public Shipment(Long id, String trackingNumber, ShipmentStatus status, double weight, User sender, User recipient, PickupPoint pickupPoint, String deliveryAddress, LocalDate shipmentDate, LocalDate estimatedDeliveryDate, DeliveryType deliveryType) {
        this.id = id;
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
    }

    public Shipment() {
    }

    public Long getId() {
        return this.id;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public ShipmentStatus getStatus() {
        return this.status;
    }

    public double getWeight() {
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

    public void setId(Long id) {
        this.id = id;
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
}
