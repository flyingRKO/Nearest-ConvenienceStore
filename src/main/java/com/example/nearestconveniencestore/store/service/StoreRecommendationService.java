package com.example.nearestconveniencestore.store.service;

import com.example.nearestconveniencestore.api.dto.DocumentDto;
import com.example.nearestconveniencestore.api.dto.KakaoApiResponseDto;
import com.example.nearestconveniencestore.api.service.KaKaoAddressSearchService;
import com.example.nearestconveniencestore.cache.RedisTemplateService;
import com.example.nearestconveniencestore.direction.dto.DirectionDto;
import com.example.nearestconveniencestore.direction.dto.OutputDto;
import com.example.nearestconveniencestore.direction.entity.Direction;
import com.example.nearestconveniencestore.direction.repository.DirectionRepository;
import com.example.nearestconveniencestore.direction.service.Base62Service;
import com.example.nearestconveniencestore.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreRecommendationService {
    private final KaKaoAddressSearchService kaKaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;
    private final RedisTemplateService redisTemplateService;
    private final DirectionRepository directionRepository;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    @Value("${store.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendStoreList(String address) {

        List<DirectionDto> directionDtoList = redisTemplateService.findByInputAddress(address);
        if (!directionDtoList.isEmpty()){
            return toOutputDtoList(toDirectionList(directionDtoList));
        }

        KakaoApiResponseDto kakaoApiResponseDto = kaKaoAddressSearchService.requestAddressSearch(address);
        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[StoreRecommendationService recommendStoreList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        Optional<List<Direction>> existingDirections = directionRepository.findDirectionByInputAddress(address);

        if (!existingDirections.isPresent() || existingDirections.get().isEmpty()) {
            List<OutputDto> noDbresult = toOutputDtoList(directionService.saveAll(directionList));
            redisTemplateService.save(toDirectionDtoList(directionList), address);
            return noDbresult;
        } else {
            List<OutputDto> result = toOutputDtoList(existingDirections.get());
            redisTemplateService.save(toDirectionDtoList(existingDirections.get()), address);
            return result;
        }
    }

    public OutputDto toOutputDto(Direction direction) {

        return OutputDto.builder()
                .storeName(direction.getTargetStoreName())
                .storeAddress(direction.getTargetAddress())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format(direction.getDistance() + " m"))
                .build();
    }

    private List<OutputDto> toOutputDtoList(List<Direction> directionList) {
        return directionList
                .stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList());
    }

    public DirectionDto toDirectionDto(Direction direction) {
        return DirectionDto.builder()
                .id(direction.getId())
                .inputAddress(direction.getInputAddress())
                .inputLatitude(direction.getInputLatitude())
                .inputLongitude(direction.getInputLongitude())
                .targetStoreName(direction.getTargetStoreName())
                .targetAddress(direction.getTargetAddress())
                .targetLatitude(direction.getTargetLatitude())
                .targetLongitude(direction.getTargetLongitude())
                .distance(direction.getDistance())
                .build();
    }

    public List<DirectionDto> toDirectionDtoList(List<Direction> directionList){
        return directionList.stream()
                .map(this::toDirectionDto)
                .collect(Collectors.toList());
    }

    public Direction toDirection(DirectionDto directionDto){
        return Direction.builder()
                .id(directionDto.getId())
                .inputAddress(directionDto.getInputAddress())
                .inputLatitude(directionDto.getInputLatitude())
                .inputLongitude(directionDto.getInputLongitude())
                .targetStoreName(directionDto.getTargetStoreName())
                .targetAddress(directionDto.getTargetAddress())
                .targetLatitude(directionDto.getTargetLatitude())
                .targetLongitude(directionDto.getTargetLongitude())
                .distance(directionDto.getDistance())
                .build();
    }

    public List<Direction> toDirectionList(List<DirectionDto> directionList){
        return directionList.stream()
                .map(this::toDirection)
                .collect(Collectors.toList());
    }
}
