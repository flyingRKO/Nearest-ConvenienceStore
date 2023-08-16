package com.example.nearestconveniencestore.store.service;

import com.example.nearestconveniencestore.store.dto.StoreDto;
import com.example.nearestconveniencestore.store.entity.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreSearchService {
    private final StoreRepositoryService storeRepositoryService;

    public List<StoreDto> searchStoreDtoList() {

        return storeRepositoryService.findAll()
                .stream()
                .map(this::convertToStoreDto)
                .collect(Collectors.toList());
    }

    private StoreDto convertToStoreDto(Store store) {

        return StoreDto.builder()
                .id(store.getId())
                .storeAddress(store.getStoreAddress())
                .storeName(store.getStoreName())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build();
    }
}
