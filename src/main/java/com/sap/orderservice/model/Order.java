package com.sap.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    private @Id @GeneratedValue Long id;
    private Long customerID;
    private String productCode;

    public Order() {
    }

    public Order(Long customerCode, String productCode) {
        this.customerID = customerCode;
        this.productCode = productCode;
    }

    public Long getId() {
        return this.id;
    }

    public String getCode() {
        return "PF0000%s".formatted(this.id);
    }

    public Long getCustomer() {
        return this.customerID;
    }

    public String getProduct() {
        return this.productCode;
    }

    public Order setCustomer(Long customerCode) {
        this.customerID = customerCode;
        return this;
    }

    public Order setProduct(String productCode) {
        this.productCode = productCode;
        return this;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", customerCode=" + customerID + ", productCode=" + productCode + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((customerID == null) ? 0 : customerID.hashCode());
        result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (customerID == null) {
            if (other.customerID != null)
                return false;
        } else if (!customerID.equals(other.customerID))
            return false;
        if (productCode == null) {
            if (other.productCode != null)
                return false;
        } else if (!productCode.equals(other.productCode))
            return false;
        return true;
    }

}
