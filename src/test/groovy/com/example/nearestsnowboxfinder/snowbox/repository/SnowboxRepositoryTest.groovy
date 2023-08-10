package com.example.nearestsnowboxfinder.snowbox.repository

import com.example.nearestsnowboxfinder.AbstractIntegrationContainerBaseTest
import com.example.nearestsnowboxfinder.snowbox.entity.Snowbox
import org.springframework.beans.factory.annotation.Autowired


class SnowboxRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private SnowboxRepository snowboxRepository

    def setup(){
        snowboxRepository.deleteAll()
    }

    def "SnowboxRepository save"() {
        given:
        String address = "대전 광역시 유성구 장대동"
        double latitude = 36.36
        double longitude = 127.32

        def snowbox = Snowbox.builder()
                .snowboxAddress(address)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = snowboxRepository.save(snowbox)

        then:
        result.getSnowboxAddress() == address
        result.getLatitude() == latitude
        result.getLongitude() == longitude

    }

    def "SnowboxRepository saveAll"() {
        given:
        String address = "대전 광역시 유성구 장대동"
        double latitude = 36.36
        double longitude = 127.32

        def snowbox = Snowbox.builder()
                .snowboxAddress(address)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        snowboxRepository.saveAll(Arrays.asList(snowbox))
        def result = snowboxRepository.findAll()

        then:
        result.size() == 1

    }

}