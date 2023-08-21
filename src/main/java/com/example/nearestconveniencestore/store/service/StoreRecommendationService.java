package com.example.nearestconveniencestore.store.service;

import com.example.nearestconveniencestore.api.dto.DocumentDto;
import com.example.nearestconveniencestore.api.dto.KakaoApiResponseDto;
import com.example.nearestconveniencestore.api.service.KaKaoAddressSearchService;
import com.example.nearestconveniencestore.api.service.KakaoCategorySearchService;
import com.example.nearestconveniencestore.direction.dto.OutputDto;
import com.example.nearestconveniencestore.direction.entity.Direction;
import com.example.nearestconveniencestore.direction.service.Base62Service;
import com.example.nearestconveniencestore.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

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
    private final Base62Service base62Service;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    @Value("${store.recommendation.base.url}")
    private String baseUrl;

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
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format(direction.getDistance() + " m"))
                .build();
    }
}
