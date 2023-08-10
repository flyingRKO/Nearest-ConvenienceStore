package com.example.nearestsnowboxfinder.api.service

import com.example.nearestsnowboxfinder.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class KaKaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KaKaoAddressSearchService kaKaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다."() {
        given:
        String address = null

        when:
        def result = kaKaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "address valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다."() {
        given:
        def address = "대전 유성구 장대동"

        when:
        def result = kaKaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.mataDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }
}
