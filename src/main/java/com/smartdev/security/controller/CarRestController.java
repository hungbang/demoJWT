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

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(HttpServletRequest request) throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        //get RSAKey

        List<Car> cars = carRepository.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(cars);
//        String result = "hello world";
        //prepare keystore
        JWKGenerator jwkGenerator = new JWKGenerator();
        JWKSet jwkSet =     jwkGenerator.getJWKSet();
        RSAKey rsaKey = (RSAKey)jwkSet.getKeyByKeyId("2708");
        KeyPair keyPair = new KeyPair(rsaKey.toPublicKey(), rsaKey.toPrivateKey());

        //to encrypt data
        Cipher encrypter = Cipher.getInstance("RSA");
        encrypter.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        encrypter.update(result.getBytes());
        String encrypted = Base64.getEncoder().encodeToString(encrypter.doFinal());
        System.out.println(encrypted);

        Cipher decrypter = Cipher.getInstance("RSA");
        decrypter.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        decrypter.update(Base64.getDecoder().decode(encrypted));
        String decrypted = new String(decrypter.doFinal());
        System.out.println("jajaja: "+ decrypted);

        System.out.println("this is data encrypted: "+ encrypted);

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

