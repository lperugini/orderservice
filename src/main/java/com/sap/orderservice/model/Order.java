package com.sap.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    private @Id @GeneratedValue Long id;
    private Long userId;
    private Long itemId;
    private Double price;
    private String description;

    public Order() {
    }

    public Order(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
        this.description = "";
        this.price = 0.0;
    }

    public Order(Long userId, Long itemId, String description, Double price) {
        this.userId = userId;
        this.itemId = itemId;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return "PF0000%s".formatted(this.id);
    }

    public Long getUser() {
        return this.userId;
    }

    public Long getItem() {
        return this.itemId;
    }

    public Order setUser(Long userId) {
        this.userId = userId;
        return this;
    }

    public Order setItem(Long itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Order setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "Order [id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", price=" + price +
                ", description='" + description + "']";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
        return result;
    }

    public Double getPrice() {
        return price;
    }

    public Order setPrice(Double price) {
        this.price = price;
        return this;
    }

}
