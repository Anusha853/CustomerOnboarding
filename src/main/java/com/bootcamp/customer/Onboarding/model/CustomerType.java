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

public class CustomerType {

    /*
      @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long customerTypeId;
        private String typeName;
        private String description;

        @OneToMany(mappedBy = "customerType")
        private List<User> users;

     */

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int customerTypeId;
        private String typeName;
        private String description;

    @OneToMany(mappedBy = "customerTypeEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

}
