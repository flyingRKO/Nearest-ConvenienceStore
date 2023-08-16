package com.example.nearestconveniencestore.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long id;
    private String storeName;
    private String storeAddress;
    private double latitude;
    private double longitude;
}
