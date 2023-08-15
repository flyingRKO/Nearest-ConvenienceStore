package com.example.nearestsnowboxfinder.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("snowbox_place")
    private String place;

    @JsonProperty("x")
    private double longitude;

    @JsonProperty("y")
    private double latitude;
}
