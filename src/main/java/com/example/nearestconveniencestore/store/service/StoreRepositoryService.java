package com.example.nearestconveniencestore.store.service;

import com.example.nearestconveniencestore.store.entity.Store;
import com.example.nearestconveniencestore.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreRepositoryService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

}
