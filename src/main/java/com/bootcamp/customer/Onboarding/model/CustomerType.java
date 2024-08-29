package com.bootcamp.customer.Onboarding.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
    public class CustomerType {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long customerTypeId;
        private String typeName;
        private String description;

        @OneToMany(mappedBy = "customerType")
        private List<User> users;

    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
