package com.smartdev.security.controller;

import com.nimbusds.jose.*;
import com.smartdev.security.param.InputParam;
import com.smartdev.security.service.JWEServiceHandler;
import com.smartdev.security.service.JWSServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@RestController
public class HomeController {

    @Autowired
    private JWEServiceHandler jweServiceHandler;
    @Autowired
    private JWSServiceHandler jwsServiceHandler;

    @PostMapping("/decrypt")
    public String decrypt(@RequestBody InputParam data) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, ParseException, JOSEException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        if("JWE".equals(data.getType())){
            return jweServiceHandler.decryptP2P(data.getValue());
        }else{
            return jwsServiceHandler.decrypt(data.getValue());
        }
    }

}
