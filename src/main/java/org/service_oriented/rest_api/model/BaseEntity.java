package org.service_oriented.rest_api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private ZonedDateTime created_date;
    @LastModifiedDate
    private ZonedDateTime updated_date;

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
