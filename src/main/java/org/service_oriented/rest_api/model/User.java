package org.service_oriented.rest_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;
import org.service_oriented.rest_api.model.enums.UserRole;

import java.time.ZonedDateTime;

@SuperBuilder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String email;

    private String phone;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(Long id, ZonedDateTime created_date, ZonedDateTime updated_date, String name, String email, String phone, Address address, UserRole role) {
        super(id, created_date, updated_date);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public User(String name, String email, String phone, Address address, UserRole role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    protected User() {
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public Address getAddress() {
        return this.address;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

