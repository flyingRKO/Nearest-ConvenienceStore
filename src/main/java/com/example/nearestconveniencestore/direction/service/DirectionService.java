package com.example.nearestconveniencestore.direction.service;

import com.example.nearestconveniencestore.api.dto.DocumentDto;
import com.example.nearestconveniencestore.api.service.KakaoCategorySearchService;
import com.example.nearestconveniencestore.direction.entity.Direction;
import com.example.nearestconveniencestore.direction.repository.DirectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionService {
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final Base62Service base62Service;

    private static final int MAX_SEARCH_CNT = 5; // 편의점 최대 검색 갯수
    private static final double RADIUS_KM = 1000; // 반경 1km

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    public Direction findById(String encodedId) {
        Long decodedId = base62Service.decodeDirectionId(encodedId);
        return directionRepository.findById(decodedId).orElse(null);
    }



    public List<Direction> buildDirectionListByCategoryApi(DocumentDto inputDocumentDto) {
        if(Objects.isNull(inputDocumentDto)) return Collections.emptyList();

        return kakaoCategorySearchService
                .requestConvenienceStoreCategorySearch(inputDocumentDto.getLatitude(), inputDocumentDto.getLongitude(), RADIUS_KM)
                .getDocumentList()
                .stream().map(resultDocumentDto ->
                        Direction.builder()
                                .inputAddress(inputDocumentDto.getAddressName())
                                .inputLatitude(inputDocumentDto.getLatitude())
                                .inputLongitude(inputDocumentDto.getLongitude())
                                .targetStoreName(resultDocumentDto.getPlaceName())
                                .targetAddress(resultDocumentDto.getAddressName())
                                .targetLatitude(resultDocumentDto.getLatitude())
                                .targetLongitude(resultDocumentDto.getLongitude())
                                .distance(resultDocumentDto.getDistance()) // m 단위
                                .build())
                .limit(MAX_SEARCH_CNT)
                .collect(Collectors.toList());
    }


}
