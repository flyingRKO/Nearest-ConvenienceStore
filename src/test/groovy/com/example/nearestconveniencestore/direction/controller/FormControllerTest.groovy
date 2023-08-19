package com.example.nearestconveniencestore.direction.controller

import com.example.nearestconveniencestore.direction.dto.OutputDto
import com.example.nearestconveniencestore.store.service.StoreRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

class FormControllerTest extends Specification {
    private MockMvc mockMvc
    private StoreRecommendationService storeRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(storeRecommendationService)).build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDto.builder()
                .storeName("store1")
                .build(),
                OutputDto.builder()
                .storeName("store2")
                .build()
        )
    }

    def "GET /"() {
        expect:
        mockMvc.perform(get("/"))
            .andExpect {handler().handlerType(FormController.class)}
            .andExpect {handler().methodName("main")}
        .andExpect {status().isOk()}
        .andExpect {view().name("main")}
        .andDo {log()}
    }

    def "POST /search"() {
        given:
        String inputAddress = "대전 유성구 장대동"

        when:
        def resultActions = mockMvc.perform(post("/search")
        .param("address", inputAddress))

        then:
        1 * storeRecommendationService.recommendStoreList(arg -> {
            assert arg == inputAddress
        }) >> outputDtoList

        resultActions
        .andExpect {status().isOk()}
        .andExpect {view().name("output")}
        .andExpect {model().attributeExists("outputFormList")}
        .andExpect {model().attribute("outputFormList", outputDtoList)}
        .andDo {print()}
    }
}
