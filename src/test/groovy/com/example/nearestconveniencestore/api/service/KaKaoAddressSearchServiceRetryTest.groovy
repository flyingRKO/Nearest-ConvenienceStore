package com.example.nearestconveniencestore.api.service

import com.example.nearestconveniencestore.AbstractIntegrationContainerBaseTest
import com.example.nearestconveniencestore.api.dto.DocumentDto
import com.example.nearestconveniencestore.api.dto.KakaoApiResponseDto
import com.example.nearestconveniencestore.api.dto.MetaDto
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import static org.springframework.http.HttpHeaders.CONTENT_TYPE


class KaKaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KaKaoAddressSearchService kaKaoAddressSearchService

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper mapper = new ObjectMapper()

    private String inputAddress = "대전 유성구 유성대로822번길"

    def setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        println mockWebServer.port
        println mockWebServer.url("/")
    }

    def cleanup() {
        mockWebServer.shutdown()
    }

    def "requestAddressSearch retry success"() {
        given:
        def metaDto = new MetaDto(1)
        def documentDto = DocumentDto.builder()
        .addressName(inputAddress)
        .build()
        def expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
        .addHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .setBody(mapper.writeValueAsString(expectedResponse)))

        def kakaoResult = kaKaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        kakaoResult.getDocumentList().size() == 1
        kakaoResult.getMataDto().totalCount == 1
        kakaoResult.getDocumentList().get(0).getAddressName() == inputAddress
    }

    def "requestAddressSearch retry fail"() {
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kaKaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.buildUriByAddressSearch(inputAddress) >> uri
        result == null
    }
}
