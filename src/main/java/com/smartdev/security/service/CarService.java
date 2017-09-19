package com.smartdev.security.service;

import com.smartdev.security.model.Car;

import java.util.List;

public interface CarService {
    List<Car> getCars();
    Car getCarById(Long id);
}
