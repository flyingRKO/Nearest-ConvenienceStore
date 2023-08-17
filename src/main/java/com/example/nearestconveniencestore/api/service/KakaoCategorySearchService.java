package com.example.nearestconveniencestore.api.service;

import com.example.nearestconveniencestore.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoCategorySearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;
    private final RestTemplate restTemplate;
    private static final String CONVENIENCE_STORE_CATEGORY = "CS2"; // 편의점 카테고리

    @Value("${KAKAO_REST_API_KEY}")
    private String kakaoRestApiKey;

    public KakaoApiResponseDto requestConvenienceStoreCategorySearch(double lat, double lon, double radius) {
        URI uri = kakaoUriBuilderService.buildUriByCategorySearch(lat, lon, radius, CONVENIENCE_STORE_CATEGORY);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
    }
}
