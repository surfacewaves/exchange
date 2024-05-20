package ru.andreev.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andreev.exchange.dto.Portfolio.PortfolioItemsDTO;
import ru.andreev.exchange.dto.Rate.LatestRatesDTO;
import ru.andreev.exchange.service.RateService;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/rates")
@RequiredArgsConstructor
@CrossOrigin
public class RateController {
    private final RateService rateService;

    @GetMapping("/latest")
    public ResponseEntity<LatestRatesDTO> getLatestRates(@RequestParam(value = "currency_name", required = false)
                                                         String currencyCode) throws InterruptedException {
        return new ResponseEntity<>(rateService.getLatestRates(currencyCode), OK);
    }

    @GetMapping("/historical")
    public ResponseEntity<LatestRatesDTO> getHistoricalRates(@RequestParam(value = "currency_name") String currencyCode,
                                                             @RequestParam(value = "currency_date") LocalDate currencyDate) throws InterruptedException {
        return new ResponseEntity<>(rateService.getHistoricalRates(currencyCode, currencyDate), OK);
    }

    @PostMapping("/count-total")
    public ResponseEntity<Double> countTotal(@RequestBody PortfolioItemsDTO portfolioItemsDTO) throws InterruptedException {
        return new ResponseEntity<>(rateService.countTotal(portfolioItemsDTO), OK);
    }
}
