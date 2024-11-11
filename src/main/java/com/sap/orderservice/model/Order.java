package com.sap.orderservice.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Table(name = "orders")
public class Order {

    private @Id @GeneratedValue Long id;
    private String customerCode;
    private String productCode;

    Order() {
    }

    public Order(String customerCode, String productCode) {
        this.customerCode = customerCode;
        this.productCode = productCode;
    }

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return String.format("PF0000%s", this.id);
    }

    public String getCustomer() {
        return this.customerCode;
    }

    public String getProduct() {
        return this.productCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomer(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setProduct(String productCode) {
        this.productCode = productCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.customerCode, this.productCode);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + this.id + ", Code='" + this.getCode() + "', Customer='" + this.customerCode + '\'' + ", Product='" + this.productCode
                + '\'' + '}';
    }
}
