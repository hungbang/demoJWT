package com.smartdev.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.smartdev.security.JWKGenerator;

import java.io.IOException;
import java.text.ParseException;

public class JOSEServiceHandler {

    private JWKGenerator generator = new JWKGenerator();

    public String encrypt(String result) throws IOException, ParseException, JOSEException {
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        Payload payLoad = new Payload(result);
        JWEObject jweObject = new JWEObject(header, payLoad);
        RSAKey keyPair = generator.getRSAKey();
        JWEEncrypter jweEncrypter = new RSAEncrypter(keyPair.toRSAPublicKey());
        jweObject.encrypt(jweEncrypter);
        return jweObject.serialize();
    }

    public String decrypt(String result) throws IOException, ParseException, JOSEException {
        System.out.println("Data to be decrypted: " + result);
        RSAKey keyPair = generator.getRSAKey();
        JWEDecrypter jweDecrypter = new RSADecrypter(keyPair.toRSAPrivateKey());
        JWEObject jweObject = JWEObject.parse(result);
        jweObject.decrypt(jweDecrypter);
        Payload payload = jweObject.getPayload();
        return payload.toString();
    }
}
