package com.example.nearestconveniencestore.cache;

import com.example.nearestconveniencestore.direction.dto.DirectionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisTemplateService {


    private final RedisTemplate<String, Object> redisTemplate;
    private final static Duration CACHE_TTL = Duration.ofDays(1);
    private final ObjectMapper objectMapper;



    public void save(List<DirectionDto> directionDtoList, String inputAddress) {
        if (Objects.isNull(directionDtoList) || directionDtoList.isEmpty() || Objects.isNull(inputAddress)) {
            log.error("Required Values must not be null");
            return;
        }

        try{
           redisTemplate.opsForValue().set(inputAddress, serializeOutputDto(directionDtoList), CACHE_TTL);
            log.info("[redis save success] input_address: {}", inputAddress);
        } catch (Exception e) {
            log.error("[redis save error] {}", e.getMessage());
        }
    }


    public List<DirectionDto> findByInputAddress(String inputAddress) {
        if (Objects.isNull(inputAddress)) {
            log.error("Required Values must not be null");
            return null;
        }

        try {
            String dtoList = (String) redisTemplate.opsForValue().get(inputAddress);
            if (dtoList == null) {
                log.warn("[redis find warning] No data found for inputAddress: {}", inputAddress);
                return Collections.emptyList();
            }
            return deserializeOutputDto(dtoList);

        } catch (Exception e) {
            log.error("[redis find error] {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(String inputAddress) {
        redisTemplate.delete(inputAddress);
        log.info("[redis delete]: {}", inputAddress);
    }

    private String serializeOutputDto(List<DirectionDto> directionDtoList) throws JsonProcessingException {
        return objectMapper.writeValueAsString(directionDtoList);
    }

    private List<DirectionDto> deserializeOutputDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value,
                new TypeReference<List<DirectionDto>>(){});
    }


}
