package com.cache.service;

import com.cache.model.DataModel;
import com.cache.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class NationalityService {

    private final DataRepository dataRepository;
    private final ExternalApiService externalApiService;

    @Autowired
    public NationalityService(DataRepository dataRepository, ExternalApiService externalApiService) {
        this.dataRepository = dataRepository;
        this.externalApiService = externalApiService;
    }

    public DataModel getData(String name) {
        Optional<DataModel> dataOpt = dataRepository.findById(name);

        if (dataOpt.isEmpty()) {
            Map<String, Object> map = externalApiService.fetchDataFromApi(name);
            DataModel data = extractHighestProbabilityCountry(name,map);
            dataRepository.save(data);
            return data;
        }

        return dataOpt.get();
    }

    public static DataModel extractHighestProbabilityCountry(String name, Map<String, Object> responseMap) {
        // Ensure that the response contains the expected structure
        if (responseMap.containsKey("country") && responseMap.get("country") instanceof List) {
            List<Map<String, Object>> countries = (List<Map<String, Object>>) responseMap.get("country");

            double highestProbability = 0.0;
            String highestProbabilityCountry = null;

            for (Map<String, Object> country : countries) {
                String countryId = (String) country.get("country_id");
                double probability = (double) country.get("probability");

                if (probability > highestProbability) {
                    highestProbability = probability;
                    highestProbabilityCountry = countryId;
                }
            }

            DataModel dataModel = new DataModel();
            dataModel.setName(name);
            dataModel.setCountryId(highestProbabilityCountry);
            dataModel.setProbability(highestProbability);
            return dataModel;
        } else {
            throw new IllegalArgumentException("Invalid response structure");
        }
    }

}
