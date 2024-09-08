package com.bootcamp.customer.Onboarding.model;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsDTO {
    private String username;
    private String name;
    private String phone;
    private int customerType;
    private String plan_name;
    private String plan_description;
    private double price;
    private int validity_days;
    private boolean document_verification;

    public boolean isDocument_verification() {
        return document_verification;
    }

    public void setDocument_verification(boolean document_verification) {
        this.document_verification = document_verification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_description() {
        return plan_description;
    }

    public void setPlan_description(String plan_description) {
        this.plan_description = plan_description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getValidity_days() {
        return validity_days;
    }

    public void setValidity_days(int validity_days) {
        this.validity_days = validity_days;
    }

    public UserDetailsDTO(String username, String name, String phone, int customerType, String plan_name, String plan_description, double price, int validity_days,boolean document_verification) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.customerType = customerType;
        this.plan_name = plan_name;
        this.plan_description = plan_description;
        this.price = price;
        this.validity_days = validity_days;
        this.document_verification=document_verification;
    }
    public UserDetailsDTO(String username, String name, String phone, int customerType,boolean document_verification) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.customerType = customerType;
        this.document_verification=document_verification;

    }
}
