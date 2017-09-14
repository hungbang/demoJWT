package com.smartdev.security.controller;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
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
    private KeyStoreDataRepository keyStoreDataRepository;

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody String data) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, ParseException, JOSEException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher decrypter = Cipher.getInstance("RSA");

        //prepare keystore
        JWKGenerator jwkGenerator = new JWKGenerator();
        JWKSet jwkSet =     jwkGenerator.getJWKSet();
        RSAKey rsaKey = (RSAKey)jwkSet.getKeyByKeyId("2708");
        KeyPair keyPair = new KeyPair(rsaKey.toPublicKey(), rsaKey.toPrivateKey());

        decrypter.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        decrypter.update(Base64.getDecoder().decode(data));
        String decrypted = new String(decrypter.doFinal());
        System.out.println(decrypted);

        return decrypted;
    }


    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, ParseException, JOSEException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String data = "oPxvpMIUzAH1MC6d+gsS8NUTtA6cctw3ryCnML3ttHVdcD1PRJkqHl6kOYPYjli9hEVqUbXRBnhNiwKqV2d5bgFTpKS11Oq6uN8Fa/yPxhnKbSOJQmtCFO/o1EEbY1u904BWnStcKVgh/xezOjZTDsHcrr8tTA7JH3Vsh2wfvf90Zmb4arx41YXPSOWmfp2INGYF5idQWlbYDB/Gc6V1rFhRI1Fh4BzHzI2qblyUWLNex6BPoDGe4jXbgcVnXc0O/Aq/tnBv5Em+xFL/jhWjpVo6XoCwcf20SSBJgjq05Vo8K/360b8N57so52Opneabiaw/eboJbwlPNFSCoPVHwQ==";
        Cipher decrypter = Cipher.getInstance("RSA");

        //prepare keystore
        JWKGenerator jwkGenerator = new JWKGenerator();
        JWKSet jwkSet =     jwkGenerator.getJWKSet();
        RSAKey rsaKey = (RSAKey)jwkSet.getKeyByKeyId("2708");
        KeyPair keyPair = new KeyPair(rsaKey.toPublicKey(), rsaKey.toPrivateKey());

        decrypter.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        decrypter.update(Base64.getDecoder().decode(data));
        String decrypted = new String(decrypter.doFinal());
        System.out.println(decrypted);

    }
}
