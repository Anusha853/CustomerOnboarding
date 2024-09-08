package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class Plans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;
    private String plan_name;
    private String plan_description;
    private double price;
    private int validity_days;

    @ManyToOne
    @JoinColumn(name = "plan_type_id")
    private PlanType plan_type;


    public Plans(){}


    public Plans(Long planId, String plan_name, String plan_description, double price, int validity_days, PlanType plan_type) {
        this.planId = planId;
        this.plan_name = plan_name;
        this.plan_description = plan_description;
        this.price = price;
        this.validity_days = validity_days;
        this.plan_type = plan_type;

    }

    public Long getPlanId() {
        return planId;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public String getPlan_description() {
        return plan_description;
    }

    public double getPrice() {
        return price;
    }

    public int getValidity_days() {
        return validity_days;
    }

    public PlanType getPlan_type() {
        return plan_type;
    }

    public void setPlanId(Long plan_id) {
        this.planId = plan_id;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public void setPlan_description(String plan_description) {
        this.plan_description = plan_description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setValidity_days(int validity_days) {
        this.validity_days = validity_days;
    }

    public void setPlan_type(PlanType plan_type) {
        this.plan_type = plan_type;
    }

    @Override
    public String toString() {
        return "Plans{" +
                "planId=" + planId +
                ", plan_name='" + plan_name + '\'' +
                ", plan_description='" + plan_description + '\'' +
                ", price=" + price +
                ", validity_days=" + validity_days +
                ", plan_type=" + plan_type +
                '}';
    }
}