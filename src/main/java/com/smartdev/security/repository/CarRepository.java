package com.smartdev.security.repository;

import com.smartdev.security.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    //User findByUsername(String username);
}
