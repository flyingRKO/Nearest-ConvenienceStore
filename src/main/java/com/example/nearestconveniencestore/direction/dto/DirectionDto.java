package com.example.nearestconveniencestore.direction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectionDto {
    private Long id;


    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;


    private String targetStoreName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;


    private int distance;
}
