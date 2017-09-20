package com.smartdev.security.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.util.Base64URL;
import com.smartdev.security.JWKGenerator;
import com.smartdev.security.exception.EncryptException;
import com.smartdev.security.service.JWEServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ResponseFilter implements ResponseBodyAdvice {

    @Autowired
    private JWEServiceHandler jWEServiceHandler;

    private JWKGenerator generator = new JWKGenerator();

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        System.out.println("========supports=========");
        /**
         * check if controller have a annotation is JsonFilter, then continue handle
         * or not then will exit
         */
        List<Annotation> annotations = Arrays.asList(methodParameter.getMethodAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(JsonFilter.class));
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ObjectMapper mapper = new ObjectMapper();
        String dataValue = "";
        try {
            dataValue = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new EncryptException("Can not parse object to json");
        }
        System.out.println(dataValue);
        if(dataValue.isEmpty()){
           throw new EncryptException("Data is body is empty");
        }
        try {
            SecretKey secretKey = generator.getAESKey();
            byte[] cek = jWEServiceHandler.encryptCEKwithRSA(secretKey);
            byte[] cipherText = jWEServiceHandler.encryptContentWithAES(dataValue, secretKey);
            JWEHeader header = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM).build();
            JWEObject jweObject = new JWEObject(header.toBase64URL(), Base64URL.encode(cek), null, Base64URL.encode(cipherText), null);
            return jweObject.serialize();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm Exception.");
        } catch (ParseException e) {
            System.out.println("Parse Exception.");
        } catch (IOException e) {
            System.out.println("IO Exception.");
        } catch (JOSEException e) {
            System.out.println("JOSE Exception.");
        } catch (BadPaddingException e) {
            System.out.println("Bad Padding Exception. "+ e.getMessage());
        } catch (IllegalBlockSizeException e) {
            System.out.println("Illegal Block Size Exception. "+ e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.out.println("No Such Padding Exception. "+ e.getMessage());
        } catch (InvalidKeyException e) {
            System.out.println("Invalid Key Exception. "+ e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
