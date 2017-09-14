package com.smartdev.security.controller;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.EncryptedJWT;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.model.KeyStoreData;
import com.smartdev.security.repository.KeyStoreDataRepository;
import com.smartdev.security.service.JOSEServiceHandler;
import com.smartdev.security.service.JWEDataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.util.Base64;

@RestController
public class HomeController {

    @Autowired
    private JOSEServiceHandler joseServiceHandler;

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String data) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, ParseException, JOSEException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return joseServiceHandler.decrypt(data);
    }

}
