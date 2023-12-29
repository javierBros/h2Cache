package com.cache.service;

import com.cache.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ExternalApiService {

    private final String externalApiUrl = "https://api.nationalize.io";
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Map<String, Object> fetchDataFromApi(String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(externalApiUrl)
                .queryParam("name", "{name}")
                .encode()
                .toUriString();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("name", name);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                urlTemplate,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                },
                uriVariables
        );

        return responseEntity.getBody();
    }
}
