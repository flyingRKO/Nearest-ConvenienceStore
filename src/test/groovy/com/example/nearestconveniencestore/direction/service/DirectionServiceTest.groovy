package com.example.nearestconveniencestore.direction.service

import com.example.nearestconveniencestore.api.dto.DocumentDto
import com.example.nearestconveniencestore.store.dto.StoreDto
import com.example.nearestconveniencestore.store.service.StoreSearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {
    private StoreSearchService searchService = Mock()
    private DirectionService directionService = new DirectionService(searchService)
    private List<StoreDto> storeList

    def setup() {
        storeList = new ArrayList<>()
        storeList.addAll(
                StoreDto.builder()
                .id(1L)
                .storeName("반석역")
                .storeAddress("주소1")
                .latitude(36.392287)
                .longitude(127.313046)
                .build(),
                StoreDto.builder()
                .id(2L)
                .storeName("유성IC ~ 충대방향")
                .storeAddress("주소2")
                .latitude(36.365034)
                .longitude(127.335502)
                .build()
        )
    }

    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는지 확인"() {
        given:
        def addressName = "유성구 유성대로822번길"
        double inputLatitude = 36.364771
        double inputLongitude = 127.339450

        def documentDto = DocumentDto.builder()
        .addressName(addressName)
        .latitude(inputLatitude)
        .longitude(inputLongitude)
        .build()

        when:
        searchService.searchStoreDtoList() >> storeList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetStoreName == "유성IC ~ 충대방향"
        results.get(1).targetStoreName == "반석역"

    }

    def "buildDirectionList - 정해진 반경 5km 내에 검색이 되는지 확인"() {
        given:
        storeList.add(
                StoreDto.builder()
                .id(3L)
                .storeName("가람아파트")
                .storeAddress("주소3")
                .latitude(36.356324)
                .longitude(127.396762)
                .build())

        def addressName = "유성구 유성대로822번길"
        double inputLatitude = 36.364771
        double inputLongitude = 127.339450

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()
        when:
        searchService.searchStoreDtoList() >> storeList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetStoreName == "유성IC ~ 충대방향"
        results.get(1).targetStoreName == "반석역"
    }
}
