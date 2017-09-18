package com.smartdev.security.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.RSAKey;
import com.smartdev.security.JWKGenerator;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

public class JWEServiceHandler {

    private JWKGenerator generator = new JWKGenerator();

    public String encryptP2P(String result) throws IOException, ParseException, JOSEException, NoSuchAlgorithmException {
        RSAKey keyPair = generator.getRSAKey();
        JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM).build();
        Payload payLoad = new Payload(result);
        JWEObject jweObject = new JWEObject(header, payLoad);
        System.out.println("Encrypted Key: "+ jweObject.getEncryptedKey());
        JWEEncrypter jweEncrypter = new RSAEncrypter(keyPair.toRSAPublicKey());
        jweObject.encrypt(jweEncrypter);
        return jweObject.serialize();
    }

    public String decryptP2P(String result) throws IOException, ParseException, JOSEException {
        System.out.println("Data to be decrypted: " + result);
        RSAKey keyPair = generator.getRSAKey();
        JWEDecrypter jweDecrypter = new RSADecrypter(keyPair.toRSAPrivateKey());
        JWEObject jweObject = JWEObject.parse(result);
        jweObject.decrypt(jweDecrypter);
        Payload payload = jweObject.getPayload();
        return payload.toString();
    }

    public static void main(String[] args) {
        System.out.println(EncryptionMethod.Family.AES_GCM.toString());
    }


}
