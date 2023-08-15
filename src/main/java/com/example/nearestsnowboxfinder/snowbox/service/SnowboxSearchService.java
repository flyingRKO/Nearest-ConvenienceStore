package com.example.nearestsnowboxfinder.snowbox.service;

import com.example.nearestsnowboxfinder.snowbox.dto.SnowboxDto;
import com.example.nearestsnowboxfinder.snowbox.entity.Snowbox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnowboxSearchService {
    private final SnowboxRepositoryService snowboxRepositoryService;

    public List<SnowboxDto> searchSnowboxDtoList() {

        return snowboxRepositoryService.findAll()
                .stream()
                .map(this::convertToSnowboxDto)
                .collect(Collectors.toList());
    }

    private SnowboxDto convertToSnowboxDto(Snowbox snowbox) {

        return SnowboxDto.builder()
                .id(snowbox.getId())
                .snowboxAddress(snowbox.getSnowboxAddress())
                .snowboxPlace(snowbox.getSnowboxPlace())
                .latitude(snowbox.getLatitude())
                .longitude(snowbox.getLongitude())
                .build();
    }
}
