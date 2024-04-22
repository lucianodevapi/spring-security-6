package com.marketinginapp.startup.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    @Column(length = 50)
    private String email;
    @Column(name = "pwd", length = 500)
    private String password;
    @Column(name = "rol", length = 20)
    private String role;
}
