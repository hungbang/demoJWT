package com.smartdev.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.util.Base64URL;
import com.smartdev.security.exception.EncryptException;
import com.smartdev.security.filter.annotation.RequestBodyFilter;
import com.smartdev.security.param.InputParam;
import com.smartdev.security.service.JWEServiceHandler;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.openssl.EncryptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonInputMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RequestBodyAdviceImpl implements RequestBodyAdvice {


    @Autowired
    private JWEServiceHandler jWEServiceHandler;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("support Request body advice");
        /**
         * check if controller have a annotation is ResponseBodyFilter, then continue handle
         * or not then will exit
         */
        List<Annotation> annotations = Arrays.asList(methodParameter.getMethodAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(RequestBodyFilter.class));
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("handle Empty Body Request body advice");
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        System.out.println("before Body Read Request body advice");
        InputStream inputStream = httpInputMessage.getBody();
        System.out.println(inputStream);
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "UTF8");
        String valueStr = writer.toString();
        System.out.println(valueStr);
        ObjectMapper objectMapper = new ObjectMapper();
        InputParam param = objectMapper.readValue(valueStr, InputParam.class);
        try {
            JWEObject object = JWEObject.parse(param.getValue());
            if (object.getState().equals(JWEObject.State.ENCRYPTED)) {
                Base64URL cekEncrypted = object.getEncryptedKey();
                Base64URL cipherTextEncrypted = object.getCipherText();
                byte[] cekDecrypted = jWEServiceHandler.decryptCEKwithRSA(cekEncrypted);
                SecretKey secretKey = new SecretKeySpec(cekDecrypted, "AES");
                byte[] cipherData = jWEServiceHandler.decryptContentWithAES(cipherTextEncrypted, secretKey);
                System.out.println(new String(cipherData));
                param.setValue(new String(cipherData));
                InputStream inputStream1 = new ByteArrayInputStream(cipherData);
                return new MappingJacksonInputMessage(inputStream1, httpInputMessage.getHeaders());
            }
        } catch (ParseException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (JOSEException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (InvalidKeyException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (NoSuchPaddingException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (BadPaddingException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (IllegalBlockSizeException e) {
            throw new EncryptException("Error occur when decrypt data.");
        } catch (InvalidAlgorithmParameterException e) {
            throw new EncryptException("Error occur when decrypt data.");
        }
        throw new EncryptException("Error occur when decrypt data.");
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        System.out.println("after Body Read Request body advice");
        System.out.println(o);
        try {
            System.out.println(httpInputMessage.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
}
