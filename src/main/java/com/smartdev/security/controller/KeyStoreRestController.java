package com.smartdev.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.model.KeyData;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

@RestController
@RequestMapping("/public/crypto/keys")
public class KeyStoreRestController {

    private JWKGenerator keyGenerator = new JWKGenerator();

    @RequestMapping(method = RequestMethod.GET)
    public KeyData findOne() throws IOException, ParseException, JOSEException {
        RSAKey rsaKey = keyGenerator.getRSAKey();
        String kid = rsaKey.getKeyID();
        ObjectMapper mapper = new ObjectMapper();
        String publicKeyvalue = mapper.writeValueAsString(rsaKey.toRSAPublicKey());
        System.out.println(publicKeyvalue);
        System.out.println(kid);
        return null;
    }


    public static void main(String[] args) throws IOException, ParseException, JOSEException {
        JWKGenerator keyGenerator = new JWKGenerator();
        RSAKey rsaKey = keyGenerator.getRSAKey();
        String kid = rsaKey.getKeyID();
        byte[] publicKeyvalue = rsaKey.toRSAPublicKey().getEncoded();
        String keyValue = StringUtils.newStringUtf8(publicKeyvalue);
        System.out.println(publicKeyvalue);
        System.out.println(kid);
    }
}
