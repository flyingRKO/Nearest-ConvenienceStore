package com.example.nearestconveniencestore.store.entity;

import com.example.nearestconveniencestore.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "store")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String storeAddress;
    private String storeName;
    private double latitude; // 위도
    private double longitude; // 경도
}
