package ru.andreev.currencyapiadapter.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.andreev.currencyapiadapter.dto.CurrencyApiResponseDTO;

@Service
@RequiredArgsConstructor
public class CurrencyApiService {
    @Value(value = "${currency-api.url}")
    private String currencyApiURL;

    @Value(value = "${currency-api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private final Gson gson;

    public CurrencyApiResponseDTO getLatestRates(String currencyName) {
        ResponseEntity<String> response = restTemplate.exchange(
                String.format("%s/%s/latest/%s", currencyApiURL, apiKey, currencyName),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });

        return gson.fromJson(response.getBody(), CurrencyApiResponseDTO.class);
    }

    public CurrencyApiResponseDTO getRatesOnRateDate(String currencyName, String date) {
        ResponseEntity<String> response = restTemplate.exchange(
                String.format("%s/%s/history/%s/%s", currencyApiURL, apiKey, currencyName, date),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                });

        return gson.fromJson(response.getBody(), CurrencyApiResponseDTO.class);
    }
}
