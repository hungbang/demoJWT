package com.smartdev.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.smartdev.security.model.Car;
import com.smartdev.security.repository.CarRepository;
import com.smartdev.security.service.JWEServiceHandler;
import com.smartdev.security.service.JWSServiceHandler;
import com.smartdev.security.service.RestEZServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.*;
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

    @Autowired
    private JWEServiceHandler jWEServiceHandler;
    @Autowired
    private JWSServiceHandler jwsServiceHandler;
    @Autowired
    private RestEZServiceHandler restEZServiceHandler;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(HttpServletRequest request) throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        //get RSAKey

        List<Car> cars = carRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(cars);
        String encrypted = jWEServiceHandler.encryptP2P(result);
        String decrypted = jWEServiceHandler.decryptP2P(encrypted);
        System.out.println("encrypted: "+encrypted);
        System.out.println("decrypted: "+decrypted);
        return encrypted;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{carId}")
    public String findOne(@PathVariable Long carId) throws IOException, ParseException, JOSEException {
        Car car = carRepository.findOne(carId);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(car);
        String encryptedStr = jwsServiceHandler.encrypt(result);
        String decryptedStr = jwsServiceHandler.decrypt(encryptedStr);
//        String encryptedStr = restEZServiceHandler.encrypte(result);
//        String decryptedStr = restEZServiceHandler.decrypte(encryptedStr);
        System.out.println("encryptedStr: "+ encryptedStr);
        System.out.println("decryptedStr: "+ decryptedStr);
        return encryptedStr;


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

