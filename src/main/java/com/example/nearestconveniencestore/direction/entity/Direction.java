package com.example.nearestconveniencestore.direction.entity;

import com.example.nearestconveniencestore.BaseTimeEntity;
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

    // 편의점
    private String targetStoreName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 사용자 주소와 편의점 주소 사이의 거리
    private int distance;
}
