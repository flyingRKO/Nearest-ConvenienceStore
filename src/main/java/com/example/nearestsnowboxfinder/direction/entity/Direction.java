package com.example.nearestsnowboxfinder.direction.entity;

import com.example.nearestsnowboxfinder.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 제설함
    private String targetSnowboxPlace;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 사용자 주소와 제설함 주소 사이의 거리
    private double distance;
}
