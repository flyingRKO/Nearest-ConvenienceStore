package com.example.nearestsnowboxfinder.direction.service;

import com.example.nearestsnowboxfinder.api.dto.DocumentDto;
import com.example.nearestsnowboxfinder.direction.entity.Direction;
import com.example.nearestsnowboxfinder.direction.repository.DirectionRepository;
import com.example.nearestsnowboxfinder.snowbox.service.SnowboxSearchService;
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
    private final SnowboxSearchService snowboxSearchService;
    private final DirectionRepository directionRepository;

    private static final int MAX_SEARCH_CNT = 3; // 제설함 최대 검색 갯수
    private static final double RADIUS_KM = 5.0; // 반경 5km

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList) {
        if (CollectionUtils.isEmpty(directionList)) return Collections.emptyList();
        return directionRepository.saveAll(directionList);
    }

    public List<Direction> buildDirectionList(DocumentDto documentDto) {

        if (Objects.isNull(documentDto)) return Collections.emptyList();

        return snowboxSearchService.searchSnowboxDtoList()
                .stream().map(snowboxDto ->
                        Direction.builder()
                                .inputAddress(documentDto.getAddressName())
                                .inputLatitude(documentDto.getLatitude())
                                .inputLongitude(documentDto.getLongitude())
                                .targetSnowboxPlace(snowboxDto.getSnowboxPlace())
                                .targetAddress(snowboxDto.getSnowboxAddress())
                                .targetLatitude(snowboxDto.getLatitude())
                                .targetLongitude(snowboxDto.getLongitude())
                                .distance(
                                        calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(),
                                                snowboxDto.getLatitude(), snowboxDto.getLongitude())
                                )
                                .build())
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_CNT)
                .collect(Collectors.toList());
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; // km
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
