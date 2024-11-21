package org.service_oriented.rest_api.model;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.List;
@SuperBuilder
@Entity
@Table(name = "pickup_points")
public class PickupPoint extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    private String workingHours;

    private int capacity;

    @OneToMany(mappedBy = "pickupPoint", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Shipment> availableShipments;

    public PickupPoint(Long id, ZonedDateTime created_date, ZonedDateTime updated_date, String name, Address address, String workingHours, int capacity, List<Shipment> availableShipments) {
        super(id, created_date, updated_date);
        this.name = name;
        this.address = address;
        this.workingHours = workingHours;
        this.capacity = capacity;
        this.availableShipments = availableShipments;
    }

    public PickupPoint(String name, Address address, String workingHours, int capacity, List<Shipment> availableShipments) {
        this.name = name;
        this.address = address;
        this.workingHours = workingHours;
        this.capacity = capacity;
        this.availableShipments = availableShipments;
    }

    protected PickupPoint() {
    }

    public String getName() {
        return this.name;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getWorkingHours() {
        return this.workingHours;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public List<Shipment> getAvailableShipments() {
        return this.availableShipments;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAvailableShipments(List<Shipment> availableShipments) {
        this.availableShipments = availableShipments;
    }
}

