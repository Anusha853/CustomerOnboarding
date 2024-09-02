package com.bootcamp.customer.Onboarding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;


    private String username;
    private String passwordHash;

    private String email;
    private String phoneNumber;

    private int customerType;

    @ManyToOne
    @JoinColumn(name = "customerType", referencedColumnName = "customerTypeId", insertable = false, updatable = false)
    private CustomerType customerTypeEntity;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserPlans> userPlans;

}
