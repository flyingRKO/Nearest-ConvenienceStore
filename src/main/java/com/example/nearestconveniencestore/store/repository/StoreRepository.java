package com.example.nearestconveniencestore.store.repository;

import com.example.nearestconveniencestore.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
