package com.smartdev.security.controller;

import com.nimbusds.jose.*;
import com.smartdev.security.filter.JsonFilter;
import com.smartdev.security.filter.RequestAdvice;
import com.smartdev.security.model.Car;
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
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class HomeController {

    @Autowired
    private JWEServiceHandler jweServiceHandler;
    @Autowired
    private JWSServiceHandler jwsServiceHandler;

    @PostMapping("/decrypt")
    @RequestAdvice
    public String decrypt(@RequestBody List<Car> datas) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException, ParseException, JOSEException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        System.out.println(String.join("==", datas.stream().map(car -> car.getDescription()).collect(Collectors.toList())));
        return String.join("==", datas.stream().map(car -> car.getDescription()).collect(Collectors.toList()));
    }

}
