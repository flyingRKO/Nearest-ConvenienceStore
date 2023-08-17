package com.example.nearestconveniencestore.direction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {

    private String storeName;
    private String storeAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;
}
