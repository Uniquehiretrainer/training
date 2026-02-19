package com.uniquehire.training.utils;

import com.uniquehire.cafe.dto.OrderResponseDTO;
import com.uniquehire.training.config.RestTemplateConfig;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
@Component
public class RestTemplateUtils {
    private final RestTemplateConfig restTemplateConfig;
    RestTemplateUtils(RestTemplateConfig restTemplateConfig){
        this.restTemplateConfig = restTemplateConfig;
    }
    public ResponseEntity<List<OrderResponseDTO>> callIntermicroServiceCommunication(String uri, HttpMethod method, @Nullable HttpEntity<?> requestEntity, ParameterizedTypeReference<List<OrderResponseDTO>> responseType){
        RestTemplate restTemplate = restTemplateConfig.getRestTemplate();
        return restTemplate.exchange(
                uri,
                method,
                requestEntity,
                responseType
        );
    }
}
