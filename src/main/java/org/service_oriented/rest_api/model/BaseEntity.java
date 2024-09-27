package org.service_oriented.rest_api.model;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private ZonedDateTime created_date;

    private ZonedDateTime updated_date;

    public BaseEntity(Long id, ZonedDateTime created_date, ZonedDateTime updated_date) {
        this.id = id;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    protected BaseEntity() {
    }

    @PrePersist
    public void prePersist() {
        this.created_date = ZonedDateTime.now();
        this.updated_date = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated_date = ZonedDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return created_date;
    }

    protected void setCreatedDate(ZonedDateTime created_date) {
        this.created_date = created_date;
    }

    public ZonedDateTime getUpdatedDate() {
        return updated_date;
    }

    protected void setUpdatedDate(ZonedDateTime updated_date) {
        this.updated_date = updated_date;
    }
}
