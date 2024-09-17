package org.service_oriented.rest_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.service_oriented.rest_api.model.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderNumber;

    @OneToMany
    private List<Shipment> shipments;

    private LocalDate orderDate;

    private double totalCost;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Long id, String orderNumber, List<Shipment> shipments, LocalDate orderDate, double totalCost, OrderStatus status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.shipments = shipments;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    protected Order() {
    }

    public Long getId() {
        return this.id;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public List<Shipment> getShipments() {
        return this.shipments;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

