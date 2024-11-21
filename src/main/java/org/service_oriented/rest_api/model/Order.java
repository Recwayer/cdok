package org.service_oriented.rest_api.model;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.service_oriented.rest_api.model.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
@SuperBuilder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(nullable = false)
    private String orderNumber;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Shipment> shipments;

    private LocalDate orderDate;

    private double totalCost;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(String orderNumber, List<Shipment> shipments, LocalDate orderDate, double totalCost, OrderStatus status) {
        this.orderNumber = orderNumber;
        this.shipments = shipments;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    protected Order() {
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

