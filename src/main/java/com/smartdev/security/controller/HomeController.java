package com.smartdev.security.controller;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.model.KeyStoreData;
import com.smartdev.security.repository.KeyStoreDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;

@RestController
public class HomeController {

    @Autowired
    private KeyStoreDataRepository keyStoreDataRepository;

    @PostMapping("/decrypt")
    public void decrypt(@RequestBody String data) {
        try{
            KeyStoreData keyStoreData = keyStoreDataRepository.findOne(Long.parseLong("1"));
            if(!StringUtils.isEmpty(data)){
                JWKGenerator generator = new JWKGenerator();
                RSAPrivateKey rsaPrivateKey = generator.rsaPrivateKey(keyStoreData.getDescription());
                RSADecrypter rsaDecrypter = new RSADecrypter(rsaPrivateKey);

                EncryptedJWT jwt = EncryptedJWT.parse(data);
                jwt.decrypt(rsaDecrypter);
                Payload payload = jwt.getPayload();
                System.out.println("Print result");
                System.out.println(payload.toString());

            }
        }catch(ParseException | JOSEException e){
            System.out.println("Error: "+ e.getMessage());
        }
    }
}
