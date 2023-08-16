package com.example.nearestconveniencestore.store.service;

import com.example.nearestconveniencestore.api.dto.DocumentDto;
import com.example.nearestconveniencestore.api.dto.KakaoApiResponseDto;
import com.example.nearestconveniencestore.api.service.KaKaoAddressSearchService;
import com.example.nearestconveniencestore.direction.entity.Direction;
import com.example.nearestconveniencestore.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnowboxRecommendationService {
    private final KaKaoAddressSearchService kaKaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendSnowboxList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kaKaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[SnowboxRecommendationService recommendSnowboxList fail] Input address: {}", address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        directionService.saveAll(directionList);

    }
}
