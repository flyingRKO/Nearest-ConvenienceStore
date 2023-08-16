package com.example.nearestsnowboxfinder.api.service

import com.example.nearestsnowboxfinder.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KaKaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KaKaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다."() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "address valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다."() {
        given:
        def address = "대전 유성구 장대동"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.mataDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }

    def "정상적인 주소를 입력했을 경우, 정상적으로 위도 경도로 변환 된다."() {

        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if(searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentList().size() > 0

        where:
        inputAddress                            | expectedResult
        "대전 광역시 유성구 장대동"                   | true
        "대전 유성구 장대동 316"                     | true
        "유성 대학로"                             | true
        "대전 유성구 봉명동 잘못된 주소"               | false
        "유성구 봉명동 606-1"                     | true
        "유성구 봉명동 606-455555"                 | false
        ""                                      | false
    }
}
