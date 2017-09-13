package com.smartdev.security.controller;

import java.util.List;

import com.smartdev.security.model.KeyStoreData;
import com.smartdev.security.repository.KeyStoreDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/keyStoreData")
public class KeyStoreDataRestController {

    @Autowired
    private KeyStoreDataRepository keyStoreDataRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<KeyStoreData> findAll() {
        return keyStoreDataRepository.findAll();
    }
    
	@RequestMapping(method = RequestMethod.GET, value = "/{keyStoreDataId}")
    public KeyStoreData findOne(@PathVariable Long keyStoreDataId) {
        return keyStoreDataRepository.findOne(keyStoreDataId);
    }
    
	@RequestMapping(method = RequestMethod.POST)
    public KeyStoreData add(@RequestBody KeyStoreData keyStoreData) {
        return keyStoreDataRepository.save(keyStoreData);
    }

	@RequestMapping(method = RequestMethod.PUT)
    public KeyStoreData update(@RequestBody KeyStoreData keyStoreData) {
        return keyStoreDataRepository.save(keyStoreData);
    }
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{keyStoreDataId}")
    public void delete(@PathVariable Long keyStoreDataId) {
        keyStoreDataRepository.delete(keyStoreDataId);
    }
	
}

