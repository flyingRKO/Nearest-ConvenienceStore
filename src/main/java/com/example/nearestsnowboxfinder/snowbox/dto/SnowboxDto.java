package com.example.nearestsnowboxfinder.snowbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnowboxDto {
    private Long id;
    private String snowboxPlace;
    private String snowboxAddress;
    private double latitude;
    private double longitude;
}
