package com.bootcamp.customer.Onboarding.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Notification implements Serializable {

    @EmbeddedId
    private NotificationId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @MapsId("planId")
    @JoinColumn(name = "planId", referencedColumnName = "planId")
    private Plans plan;

    @Column(nullable = false)
    private boolean status;

}