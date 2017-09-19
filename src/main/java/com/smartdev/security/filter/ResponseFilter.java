package com.smartdev.security.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.util.Base64URL;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.service.JWEDataHelper;
import com.smartdev.security.service.JWEServiceHandler;
import com.smartdev.security.service.JWSServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.List;
import java.util.zip.GZIPOutputStream;

//@ControllerAdvice
public class ResponseFilter implements ResponseBodyAdvice {

    @Autowired
    private JWEServiceHandler jWEServiceHandler;
    @Autowired
    private JWSServiceHandler jwsServiceHandler;

    private JWKGenerator generator = new JWKGenerator();

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        System.out.println("========supports=========");
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ObjectMapper mapper = new ObjectMapper();
        String dataValue = "";
        try {
            dataValue = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.out.println("Json Processing Exception."+ e.getMessage());
        }
        System.out.println(dataValue);
        if(dataValue.isEmpty()){
            System.out.println("Can not encript data.");
            return o;
        }
        try {
            SecretKey secretKey = generator.getAESKey();
            byte[] cek = jWEServiceHandler.encryptCEKwithRSA(secretKey);
            byte[] cipherText = jWEServiceHandler.encryptContentWithAES(dataValue, secretKey);
            JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).build();
            JWEObject jweObject = new JWEObject(header.toBase64URL(), Base64URL.encode(cek), null, Base64URL.encode(cipherText), null);
            return jweObject.serialize();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exception. "+ e.getMessage());
        } catch (ParseException e) {
            System.out.println("Parse Exception. "+ e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception. "+ e.getMessage());
        } catch (JOSEException e) {
            System.out.println("JOSE Exception. "+ e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
