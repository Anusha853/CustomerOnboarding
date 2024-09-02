package com.bootcamp.customer.Onboarding.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_plan_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private Plans plan;

    private Date activation_date;
    private Date expiration_date;


}