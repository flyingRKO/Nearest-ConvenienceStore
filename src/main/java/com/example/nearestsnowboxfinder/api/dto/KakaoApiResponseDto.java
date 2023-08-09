package com.example.nearestsnowboxfinder.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoApiResponseDto {

    @JsonProperty("meta")
    private MetaDto mataDto;

    @JsonProperty("documents")
    private List<DocumentDto> documentList;
}
