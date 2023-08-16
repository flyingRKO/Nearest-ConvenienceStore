package com.example.nearestconveniencestore.store.repository

import com.example.nearestconveniencestore.AbstractIntegrationContainerBaseTest
import com.example.nearestconveniencestore.store.entity.Store
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class StoreRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private StoreRepository storeRepository

    def setup(){
        storeRepository.deleteAll()
    }

    def "StoreRepository save"() {
        given:
        String address = "대전 광역시 유성구 장대동"
        double latitude = 36.36
        double longitude = 127.32

        def store = Store.builder()
                .storeAddress(address)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def result = storeRepository.save(store)

        then:
        result.getStoreAddress() == address
        result.getLatitude() == latitude
        result.getLongitude() == longitude

    }

    def "StoreRepository saveAll"() {
        given:
        String address = "대전 광역시 유성구 장대동"
        double latitude = 36.36
        double longitude = 127.32

        def store = Store.builder()
                .storeAddress(address)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        storeRepository.saveAll(Arrays.asList(store))
        def result = storeRepository.findAll()

        then:
        result.size() == 1

    }

    def "BaseTimeEntity 등록"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        String address = "대전 광역시 유성구 장대동"

        def store = Store.builder()
                .storeAddress(address)
                .build()

        when:
        storeRepository.save(store)
        def result = storeRepository.findAll()

        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)
    }

}