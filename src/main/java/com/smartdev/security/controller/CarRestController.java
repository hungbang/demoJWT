package com.smartdev.security.controller;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.JwtTokenUtil;
import com.smartdev.security.model.Car;
import com.smartdev.security.model.KeyStoreData;
import com.smartdev.security.repository.CarRepository;
import com.smartdev.security.repository.KeyStoreDataRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.util.DerValue;

import javax.servlet.http.HttpServletRequest;


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

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(HttpServletRequest request) throws ParseException, JOSEException, JsonProcessingException {
        String authToken = request.getHeader(this.tokenHeader);
        KeyStoreData keyStoreData = keyStoreDataRepository.findOne(Long.parseLong("1"));
        String keyString = keyStoreData.getDescription();
        JWKGenerator jwkGenerator = new JWKGenerator();
        RSAPublicKey publicKey = jwkGenerator.rsaPublicKey(keyString);
        JWEEncrypter rsaEncrypter = new RSAEncrypter(publicKey);
        List<Car> cars = carRepository.findAll();
        JSONObject jsonObject = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        Payload payload = new Payload(objectMapper.writeValueAsString(cars));
//        JWEObject jweObject = new JWEObject(header, payload);
//        jweObject.encrypt(rsaEncrypter);
//        String result = jweObject.serialize();
        return null;
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

