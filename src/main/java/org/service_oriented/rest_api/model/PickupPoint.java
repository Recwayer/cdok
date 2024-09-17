package org.service_oriented.rest_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "pickup_points")
public class PickupPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Address address;

    private String workingHours;

    private int capacity;

    @OneToMany
    private List<Shipment> availableShipments;

    public PickupPoint(Long id, String name, Address address, String workingHours, int capacity, List<Shipment> availableShipments) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.workingHours = workingHours;
        this.capacity = capacity;
        this.availableShipments = availableShipments;
    }

    protected PickupPoint() {
    }

    public Long getId() {
        return this.id;
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

    public void setId(Long id) {
        this.id = id;
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

