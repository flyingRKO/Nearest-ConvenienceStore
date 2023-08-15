package com.example.nearestsnowboxfinder.direction.service

import com.example.nearestsnowboxfinder.api.dto.DocumentDto
import com.example.nearestsnowboxfinder.snowbox.dto.SnowboxDto
import com.example.nearestsnowboxfinder.snowbox.service.SnowboxSearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {
    private SnowboxSearchService searchService = Mock()
    private DirectionService directionService = new DirectionService(searchService)
    private List<SnowboxDto> snowboxList

    def setup() {
        snowboxList = new ArrayList<>()
        snowboxList.addAll(
                SnowboxDto.builder()
                .id(1L)
                .snowboxPlace("반석역")
                .snowboxAddress("주소1")
                .latitude(36.392287)
                .longitude(127.313046)
                .build(),
                SnowboxDto.builder()
                .id(2L)
                .snowboxPlace("유성IC ~ 충대방향")
                .snowboxAddress("주소2")
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
        searchService.searchSnowboxDtoList() >> snowboxList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetSnowboxPlace == "유성IC ~ 충대방향"
        results.get(1).targetSnowboxPlace == "반석역"

    }

    def "buildDirectionList - 정해진 반경 5km 내에 검색이 되는지 확인"() {
        given:
        snowboxList.add(
                SnowboxDto.builder()
                .id(3L)
                .snowboxPlace("가람아파트")
                .snowboxAddress("주소3")
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
        searchService.searchSnowboxDtoList() >> snowboxList
        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetSnowboxPlace == "유성IC ~ 충대방향"
        results.get(1).targetSnowboxPlace == "반석역"
    }
}
