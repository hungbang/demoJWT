package com.smartdev.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.*;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.JwtTokenUtil;
import com.smartdev.security.model.Car;
import com.smartdev.security.repository.CarRepository;
import com.smartdev.security.repository.KeyStoreDataRepository;
import com.smartdev.security.service.JOSEServiceHandler;
import com.smartdev.security.service.JWEDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.*;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/cars")
public class CarRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("$jwt.key.private")
    private String privateKeyB64;

    @Autowired
    private KeyStoreDataRepository keyStoreDataRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private JWKGenerator generator;

    @Autowired
    private JWEDataHelper jweDataHelper;

    @Autowired
    private JOSEServiceHandler joseServiceHandler;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(HttpServletRequest request) throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        //get RSAKey

        List<Car> cars = carRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(cars);
//        String result = "hello world";
        //prepare keystore
//        KeyPair keyPair = generator.getKeyPair();
//        //to encrypt data
//        String encrypted = jweDataHelper.encryptData(keyPair, result);
//        String decrypted = jweDataHelper.decryptData(keyPair, encrypted);
//        System.out.println("jajaja: " + decrypted);
//        System.out.println("this is data encrypted: " + encrypted);

        String encrypted = joseServiceHandler.encrypt(result);

        String decrypted = joseServiceHandler.decrypt(encrypted);
        System.out.println("encrypted: "+encrypted);
        System.out.println("decrypted: "+decrypted);
        return encrypted;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{carId}")
    public Car findOne(@PathVariable Long carId) {
        return carRepository.findOne(carId);
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

