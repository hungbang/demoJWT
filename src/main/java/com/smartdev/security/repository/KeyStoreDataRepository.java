package com.smartdev.security.repository;

import com.smartdev.security.model.KeyStoreData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyStoreDataRepository extends JpaRepository<KeyStoreData, Long> {
    //User findByUsername(String username);
}
