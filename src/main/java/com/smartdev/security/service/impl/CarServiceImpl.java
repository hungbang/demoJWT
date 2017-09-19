package com.smartdev.security.service.impl;

import com.smartdev.security.model.Car;
import com.smartdev.security.repository.CarRepository;
import com.smartdev.security.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public List<Car> getCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        return carRepository.findOne(id);
    }
}
