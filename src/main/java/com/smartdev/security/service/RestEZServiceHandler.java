package com.smartdev.security.service;

import com.nimbusds.jose.JOSEException;
import com.smartdev.security.JWKGenerator;
import org.jboss.resteasy.jose.jwe.JWEBuilder;
import org.jboss.resteasy.jose.jwe.JWEInput;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class RestEZServiceHandler {

    private JWKGenerator generator = new JWKGenerator();

    public String encrypte(String data) throws IOException, ParseException, JOSEException {
        KeyPair keyPair = generator.getKeyPair();
        String encoded = new JWEBuilder().contentBytes(data.getBytes()).RSA_OAEP((RSAPublicKey)keyPair.getPublic());
        System.out.println("encoded: "+ encoded);
        return encoded;
    }

    public String decrypte(String data) throws IOException, ParseException, JOSEException {
        KeyPair keyPair = generator.getKeyPair();
        byte[] decoded = new JWEInput(data).decrypt((RSAPrivateKey)keyPair.getPrivate()).getRawContent();
        String decodedStr = StringUtils.newStringUtf8(decoded);
        System.out.println("encoded: "+ decodedStr);
        return decodedStr;
    }
}
