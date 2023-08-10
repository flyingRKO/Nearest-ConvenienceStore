package com.example.nearestsnowboxfinder.snowbox.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "snowbox")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Snowbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String snowboxAddress;
    private double latitude; // 위도
    private double longitude; // 경도
}
