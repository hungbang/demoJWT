package com.smartdev.security.controller;

import com.nimbusds.jose.JOSEException;
import com.smartdev.security.filter.annotation.ResponseBodyFilter;
import com.smartdev.security.model.Car;
import com.smartdev.security.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/cars")
public class CarRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("$jwt.key.private")
    private String privateKeyB64;

    @Autowired
    private CarRepository carRepository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBodyFilter
    public ResponseEntity<?> findAll(HttpServletRequest request) throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        List<Car> cars = carRepository.findAll();
        return ResponseEntity.ok(cars);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{carId}")
    public ResponseEntity<?> findOne(@PathVariable Long carId) throws IOException, ParseException, JOSEException {
        Car car = carRepository.findOne(carId);
        return ResponseEntity.ok(car);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Car add(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Car update(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{carId}")
    public void delete(@PathVariable Long carId) {
        carRepository.delete(carId);
    }

}

