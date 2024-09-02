package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plan_id;
    private String plan_name;
    private String plan_description;
    private double price;
    private int validity_days;

    @ManyToOne
    @JoinColumn(name = "plan_type_id")
    private PlanType plan_type;


}