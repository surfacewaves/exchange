package ru.andreev.user.service.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.andreev.user.dto.rest.PortfolioItemsDTO;

@Service
@RequiredArgsConstructor
public class ExchangeRestService {

    @Value(value = "${exchange-service.url}")
    private String exchangeServiceUrl;

    private final RestTemplate restTemplate;

    public Double countTotal(PortfolioItemsDTO portfolioItemsDTO) {
        HttpEntity<PortfolioItemsDTO> httpEntity = new HttpEntity<>(portfolioItemsDTO);

        ResponseEntity<Double> response = restTemplate.exchange(
                String.format("%s/v1/rates/count-total", exchangeServiceUrl),
                HttpMethod.POST, httpEntity, new ParameterizedTypeReference<Double>() {
                });
        return response.getBody();
    }
}
