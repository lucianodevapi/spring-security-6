package com.marketinginapp.startup.repositories;

import com.marketinginapp.startup.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, BigInteger> {
    Optional<CustomerEntity> findByEmail(String email);
}
