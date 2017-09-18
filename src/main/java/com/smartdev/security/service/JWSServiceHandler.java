package com.smartdev.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.RSAKey;
import com.smartdev.security.JWKGenerator;
import org.springframework.util.Assert;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class JWSServiceHandler {

    private JWKGenerator generator = new JWKGenerator();

    public String encrypt(String result) throws IOException, ParseException, JOSEException {
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("2708").build();
        Payload payLoad = new Payload(result);
        JWSObject jwsObject = new JWSObject(jwsHeader, payLoad);
        KeyPair keyPair = generator.getKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        JWSSigner signer = new RSASSASigner(privateKey);
        jwsObject.sign(signer);
        String encryptedStr = jwsObject.serialize();
        System.out.println(encryptedStr);
        return encryptedStr;
    }

    public String decrypt(String result) throws IOException, ParseException, JOSEException {
        System.out.println("Data to be decrypted: " + result);
        KeyPair keyPair = generator.getKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        JWSObject jwsObject = JWSObject.parse(result);
        JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);
        System.out.println(jwsObject.verify(jwsVerifier));
        Assert.isTrue(jwsObject.verify(jwsVerifier));
        return jwsObject.getPayload().toString();
    }
}
