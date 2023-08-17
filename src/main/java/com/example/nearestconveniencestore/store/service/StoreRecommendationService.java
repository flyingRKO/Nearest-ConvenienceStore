package com.example.nearestconveniencestore.store.service;

import com.example.nearestconveniencestore.api.dto.DocumentDto;
import com.example.nearestconveniencestore.api.dto.KakaoApiResponseDto;
import com.example.nearestconveniencestore.api.service.KaKaoAddressSearchService;
import com.example.nearestconveniencestore.api.service.KakaoCategorySearchService;
import com.example.nearestconveniencestore.direction.dto.OutputDto;
import com.example.nearestconveniencestore.direction.entity.Direction;
import com.example.nearestconveniencestore.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreRecommendationService {
    private final KaKaoAddressSearchService kaKaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDto> recommendStoreList(String address) {
        KakaoApiResponseDto kakaoApiResponseDto = kaKaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[StoreRecommendationService recommendStoreList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto);

        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());

    }

    private OutputDto convertToOutputDto(Direction direction) {
        return OutputDto.builder()
                .storeName(direction.getTargetStoreName())
                .storeAddress(direction.getTargetAddress())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format(direction.getDistance() + " m"))
                .build();
    }
}
